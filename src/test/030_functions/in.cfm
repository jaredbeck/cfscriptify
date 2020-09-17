<cffunction name="die" returntype="void" output="yes">
	<cfargument name="errorText" type="string" required="yes">
	<cfargument name="includeHeader" type="boolean" required="no" default="no">
	<cfargument name="includeFooter" type="boolean" required="no" default="yes">

	<cfset request.newObj('die').init(errorText=arguments.errorText
			, header=includeHeader
			, footer=includeFooter
			, isPopup=false).draw()>
</cffunction>
<cfset request.die = die>


<cffunction name="deny" returntype="void" output="yes">
	<cfargument name="includeHeader" type="boolean" required="no" default="no">
	<cfargument name="includeFooter" type="boolean" required="no" default="yes">
	<cfargument name="includeWinFooter" type="boolean" required="no" default="no">
	<cfargument name="errorText" type="string" required="no" default="">

	<cfif (arguments.includeFooter or arguments.includeHeader) and arguments.includeWinFooter>
		<cfthrow message="Can't pass includeFooter=1 or includeHeader=1 when includeWinFooter==1">
	</cfif>

	<cfset request.newObj('deny').init(errorText=arguments.errorText
			, header=includeHeader
			, footer=includeFooter
			, isPopup=false).draw()>
</cffunction>
<cfset request.deny = deny>
<cfset StructDelete(variables, 'deny')>
