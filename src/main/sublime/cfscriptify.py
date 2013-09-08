import sublime, sublime_plugin
import subprocess

class CfscriptifyCommand(sublime_plugin.TextCommand):
  def run(self, edit):
    cfs_path = "/Users/jared/git/cfscriptify/bin/run.sh"
    tmp_file_path = "/tmp/cfscriptify_sublime_plugin.txt"
    selection = self.view.sel()
    for region in selection:
      if not region.empty():
        region_text = self.view.substr(region)

        tmp_file = open(tmp_file_path, "w")
        tmp_file.write(region_text)
        tmp_file.close()

        p = subprocess.Popen(['java', '-classpath', '".:/Users/jared/git/cfscriptify:/Users/jared/git/cfscriptify/antlr-4.0-complete.jar:/Users/jared/git/cfscriptify/commons-lang3-3.1.jar:"', 'CFScriptify', tmp_file_path], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = p.communicate()

        final = "{0} {1}".format(out, err)

        self.view.replace(edit, region, final)
