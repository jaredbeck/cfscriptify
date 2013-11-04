<cflock timeout="1">
  <cfset Sleep(Rand('SHA1PRNG') * 2000)>
</cflock>

<cflock timeout="#Rand('SHA1PRNG') * 1000#">
  <cfset Sleep(500)>
</cflock>

<cflock timeout="1" name="x" type="exclusive">
  <cflock timeout="1" name="y" type="exclusive">
  </cflock>
</cflock>

<cflock timeout="1" scope="Application" type="readOnly"></cflock>
