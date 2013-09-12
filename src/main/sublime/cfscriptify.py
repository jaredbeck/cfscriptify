import sublime, sublime_plugin
import subprocess

class CfscriptifyCommand(sublime_plugin.TextCommand):

  def java_classpath(self):
    return '".:/Users/jared/git/cfscriptify/target:/Users/jared/git/cfscriptify/antlr-4.0-complete.jar:/Users/jared/git/cfscriptify/commons-lang3-3.1.jar:"'

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
