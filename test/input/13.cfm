<cfloop from="#a#" to="#b#" index="local.i">
  <cfset foobar()>
</cfloop>

<cfloop from="10" to="1" index="local.i" step="#-1 * c#">
  <cfset foobar()>
</cfloop>

<cfloop from="#a#" to="#b#" index="local.i" step="#c#">
  <cfset foobar()>
</cfloop>

<cfloop from="1.0" to="0.0" index="local.i" step="-0.1">
  <cfset foobar()>
</cfloop>
