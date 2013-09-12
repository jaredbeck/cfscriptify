#!/usr/bin/env ruby

$stdout.sync = true

class Test
  attr_reader :number, :infile, :outfile

  def initialize infile, outfile
    @infile = infile
    @outfile = outfile
    @number = File.basename(@infile.path, ".cfm")
  end

  def run
    system "bin/run.sh < #{infile.path} > #{tmp_file_path}"
    result = File.read(tmp_file_path)
    expected = outfile.read
    pass = result == expected
    if (pass)
      green "."
    else
      red "\nTest #{number.to_i} failed:\n"
      system "diff -U 3 #{tmp_file_path} #{outfile.path} | tail +4"
    end
  end

  def green str
    print "\e[32m" + str.to_s + "\e[0m"
  end

  def red str
    print "\e[31m" + str.to_s + "\e[0m"
  end

  def tmp_file_path
    "/tmp/cfscriptify_test"
  end
end

class TestSuite
  INPUTS = 'src/test/input'
  OUTPUTS = 'src/test/output'

  def run
    tests.map(&:run)
    puts # LF
  end

  def paths
    Dir.glob "#{INPUTS}/*.cfm"
  end

  def tests
    paths.map { |p|
      outfile = File.join([OUTPUTS, File.basename(p)])
      Test.new(File.new(p), File.new(outfile))
    }
  end
end

TestSuite.new.run
