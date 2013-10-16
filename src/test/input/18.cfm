<cffunction name="foo" returntype="void" output="false" access="public">
  <cfargument name="a" type="numeric" required="true">
  <cfargument name="b" type="numeric" required="true" hint="discard" displayname="discard">
  <cfargument name="c" type="numeric" required="false" default="C">
  <cfargument name="d">
  <cfabort>
</cffunction>
