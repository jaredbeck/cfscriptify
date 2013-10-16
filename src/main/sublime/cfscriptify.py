import sublime, sublime_plugin
import subprocess

class CfscriptifyCommand(sublime_plugin.TextCommand):

  def jars(self):
    return [
      "/Users/jared/git/cfscriptify/target/cfscriptify-0.0.1.jar"
      , "/Users/jared/.m2/repository/org/antlr/antlr4-runtime/4.1/antlr4-runtime-4.1.jar"
      , "/Users/jared/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar"
    ]

  def java_classpath(self):
    return '".:{0}:"'.format(":".join(self.jars()))

  def run(self, edit):
    cfs_path = "/Users/jared/git/cfscriptify/run.sh"
    selection = self.view.sel()
    for region in selection:
      if not region.empty():
        region_text = self.view.substr(region)
        p = subprocess.Popen(['java', '-classpath', self.java_classpath(), 'CFScriptify'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = p.communicate(input=region_text)
        stdout_and_stderr = "{0} {1}".format(out, err)
        self.view.replace(edit, region, stdout_and_stderr)
