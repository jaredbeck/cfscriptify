<cfloop from="1" to="10" index="i">
  <cfif i MOD 3><!--- fizz ---><cfcontinue></cfif>
  <cfif i MOD 5><!--- buzz ---><cfbreak></cfif>
</cfloop>
