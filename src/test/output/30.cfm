void function die(required string errorText, boolean includeHeader = "no", boolean includeFooter = "yes") {
	request.newObj('die').init(errorText = arguments.errorText, header = includeHeader, footer = includeFooter, isPopup = false).draw();
}
request.die = die;
void function deny(boolean includeHeader = "no", boolean includeFooter = "yes", boolean includeWinFooter = "no", string errorText = "") {
	if ((arguments.includeFooter or arguments.includeHeader) and arguments.includeWinFooter) {
		Throw(message="Can't pass includeFooter=1 or includeHeader=1 when includeWinFooter==1");
	}
	request.newObj('deny').init(errorText = arguments.errorText, header = includeHeader, footer = includeFooter, isPopup = false).draw();
}
request.deny = deny;
StructDelete(variables, 'deny');
