<!--- Check permissions --->
<cfif not ((session.stPerm.canAccessAllData or val(session.stPerm.canAccessClientData) eq request.app_clientID) and (session.stPerm.canAccessAllModules or val(session.stPerm.canAccessClientModules) eq request.app_clientID))>
  <cfset request.deny(1)>
</cfif>
