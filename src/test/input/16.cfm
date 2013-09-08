<cftry>
  <cfthrow message="kaboom">
  <cfcatch type="database">
    <cfset foo = cfcatch.message>
    <cfrethrow>
  </cfcatch>
  <cfcatch>
    <cfabort>
  </cfcatch>
</cftry>
