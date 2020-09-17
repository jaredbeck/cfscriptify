<cffunction name="getSpecialTableName" access="public" output="false" returntype="string">
  <cfif this.isTT()>
    <cfreturn this.specialTableName>
  <cfelse>
    <cfreturn Replace(this.specialTableName, '_tt_', '_ntt_')>
  </cfif>
</cffunction>
