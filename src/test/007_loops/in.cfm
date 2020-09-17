<cfloop list="#lst#" index="i">
  <cfset foobar()>
</cfloop>

<cfloop array="#ary#" index="elm">
  <cfset foobar()>
</cfloop>

<cfloop from="10" to="1" index="local.i" step="-1">
  <cfset foobar()>
</cfloop>
