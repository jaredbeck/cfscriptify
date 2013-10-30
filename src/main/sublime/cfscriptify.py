import sublime, sublime_plugin
import subprocess

class CfscriptifyCommand(sublime_plugin.TextCommand):

  def cfs_path(self):
    return "/Users/jared/git/cfscriptify/bin/run.sh"

  def run(self, edit):
    selection = self.view.sel()
    for region in selection:
      if not region.empty():
        region_text = self.view.substr(region)
        p = subprocess.Popen([self.cfs_path()], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = p.communicate(input=region_text)
        stdout_and_stderr = "{0} {1}".format(out, err)
        self.view.replace(edit, region, stdout_and_stderr)
