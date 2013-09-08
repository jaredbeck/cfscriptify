<!--- get cycleid --->
<cfloop list="#fieldList#" index="curField2">
  <cfif FindNoCase("cycle_for_surv_id_", curField2)>
    <cfset sid = Mid(curField2, Len("cycle_for_surv_id_") + 1, Len(curField2) - Len("cycle_for_surv_id_"))>
    <cfif sid EQ stSurvExt.surveyid>
      <cfset stSurvExt.cycleid = form[curField2]>
    </cfif>
  </cfif>
</cfloop>
