<cfswitch expression="#foo EQ '>'#">
	<cfcase value="bar">
		<!--- A --->
	</cfcase>
	<cfcase value="baz">
		<!--- B --->
	</cfcase>
	<cfdefaultcase>
		<!--- C --->
	</cfdefaultcase>
</cfswitch>
