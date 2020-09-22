#!/usr/bin/env ruby

$stdout.sync = true

require 'open3'

class Test
  attr_reader :infile, :outfile

  def initialize(infile, outfile, name)
    @infile = infile
    @outfile = outfile
    @name = name
  end

  def number
    @_number ||= @name.match(/\A\d+/)[0].to_i
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
    @tests = find_tests(opts.fetch(:tests))
  end

  def run
    compile
    results.each(&:print)
    puts # LF
  end

  private

  # `clean` is probably only necessary when `target/generated-sources` changes
  # dramatically. For example, when I split the "combined grammar" into separate
  # lexer/parser grammers, `CFMLBaseListener.java` changed to
  # `CFMLParserBaseListener.java`, and so `clean` was necessary to delete the
  # former. Regardless, we always `clean`, because we don't want to think about
  # it.
  def compile
    return unless @compile
    `mvn --quiet clean compile assembly:single`
  end

  def parse_cli_args(args)
    config = { compile: true }
    options, nonoptions = args.partition { |i| i.start_with?('--') }
    config.merge!(parse_cli_options(options))
    config[:tests] = parse_cli_test_numbers(nonoptions)
    config
  end

  def parse_cli_options(options)
    options.each_with_object({}) do |e, a|
      case e
      when '--skip-compile'
        a[:compile] = false
      else
        warn "Invalid option: #{e}"
        print_usage_and_exit
      end
    end
  end

  def parse_cli_test_numbers(nonoptions)
    return :all if nonoptions.length == 0
    test_numbers = nonoptions.map(&:to_i)
    if test_numbers.none?(&:zero?)
      test_numbers
    else
      print_usage_and_exit
    end
  end

  def print_usage_and_exit
    warn 'Usage: bin/test.rb [--skip-compile] [test number]'
    exit 1
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

  # Optimization: to avoid JVM startup cost, we only execute `cmd` once. The
  # downside of this optimization is that if some input causes the `cmd` to
  # crash, we just get stderr and it doesn't tell us which test crashed.
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

  def find_tests(requested)
    dir = Dir.new(File.join(__dir__, '..', 'src', 'test'))
    found = dir.children.map { |test_dir|
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
    if requested == :all
      found
    else
      matched = found.select { |t| requested.include?(t.number) }
      if matched.empty?
        warn format('%d tests found, but none matched %s', found.length, requested.inspect)
        exit 2
      else
        matched
      end
    end
  end
end

TestSuite.new(*ARGV).run
