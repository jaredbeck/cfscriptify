/* get cycleid */
for (curField2 in ListToArray(fieldList)) {
	if (FindNoCase("cycle_for_surv_id_", curField2)) {
		sid = Mid(curField2, Len("cycle_for_surv_id_") + 1, Len(curField2) - Len("cycle_for_surv_id_"));
		if (sid EQ stSurvExt.surveyid) {
			stSurvExt.cycleid = form[curField2];
		}
	}
}
