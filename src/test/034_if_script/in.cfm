<cfif form.action EQ "recode">
	<cfscript>
		recodeUnit(objUnit);
		deleteUnit(objUnit);
	</cfscript>
<cfelseif form.action eq "delete">
	<cfscript>
		deleteUnit(objUnit);
	</cfscript>
</cfif>
