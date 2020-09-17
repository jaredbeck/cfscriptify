<cftry>
  <cfthrow message="a">
  <cfcatch>
    <cftry>
      <cfthrow message="b">
      <cfcatch>
        <!--- 1 --->
      </cfcatch>
      <cffinally>
        <!--- 2 --->
      </cffinally>
    </cftry>
  </cfcatch>
  <cffinally>
    <!--- 3 --->
  </cffinally>
</cftry>
