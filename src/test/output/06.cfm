/* Check permissions */
if (not ((session.stPerm.canAccessAllData or val(session.stPerm.canAccessClientData) eq request.app_clientID) and (session.stPerm.canAccessAllModules or val(session.stPerm.canAccessClientModules) eq request.app_clientID))) {
	request.deny(1);
}
