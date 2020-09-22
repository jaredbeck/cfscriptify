#!/usr/bin/env ruby

$stdout.sync = true

require 'open3'

class Test
  attr_reader :number, :infile, :outfile

  def initialize(infile, outfile, number)
    @infile = infile
    @outfile = outfile
    @number = number
  end
end

class Result
  attr_reader :test, :actual

  TMP_FILE = "/tmp/cfscriptify_test"

  def initialize test, actual
    @test = test
    @actual = actual
  end

  def print
    expected = test.outfile.read
    if expected == actual
      green "."
    else
      red "\nTest #{number} failed\n"
      File.open(TMP_FILE, "w") { |f| f.write(actual) }
      system "diff -U 3 #{test.outfile.path} #{TMP_FILE} | tail +4"
    end
  end

  private

  def green str
    $stdout.print "\e[32m" + str.to_s + "\e[0m"
  end

  def red str
    $stdout.print "\e[31m" + str.to_s + "\e[0m"
  end

  def number
    test.number
  end
end

class TestSuite
  def initialize(*args)
    opts = parse_cli_args(args)
    @compile = opts.fetch(:compile)
    @tests = find_tests
  end

  def run
    compile
    results.each(&:print)
    puts # LF
  end

  private

  def compile
    return unless @compile
    `mvn --quiet compile assembly:single`
  end

  def parse_cli_args(args)
    if args.length == 0
      { compile: true }
    elsif args == ['--skip-compile']
      { compile: false }
    else
      warn 'Usage: bin/test.rb [--skip-compile]'
      exit 1
    end
  end

  def results
    run_concatenated.
      zip(@tests). # Correlate test with actual output
      map { |a|
        Result.new(
          a[1], # the `Test`
          a[0] # the actual output
        )
      }
  end

  # Optimization: to avoid JVM startup cost, we only execute `cmd` once.
  def run_concatenated
    Open3.popen3(cmd) { |stdin, stdout, stderr, wait_thr|
      @tests.each do |test|
        stdin.print test.infile.read
        stdin.print "<!--- most awkward delimiter, ever --->"
      end
      stdin.close
      $stderr.print(stderr.read)
      stdout.read
    }.split("/* most awkward delimiter, ever */\n")
  end

  def cmd
    "java -jar target/cfscriptify-0.0.1.jar"
  end

  def paths
    Dir.glob "#{INPUTS}/*.cfm"
  end

  def find_tests
    dir = Dir.new(File.join(__dir__, '..', 'src', 'test'))
    dir.children.map { |test_dir|
      infile = File.join(dir, test_dir, 'in.cfm')
      outfile = File.join(dir, test_dir, 'out.cfm')
      if !File.exists?(infile)
        warn "File not found: #{infile}"
      elsif !File.exists?(outfile)
        warn "File not found: #{outfile}"
      else
        Test.new(File.new(infile), File.new(outfile), test_dir)
      end
    }.compact
  end
end

TestSuite.new(*ARGV).run
