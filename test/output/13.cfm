for (local.i = a; i LTE b; i++) {
	foobar();
}
for (local.i = 10; i GTE 1; i += -1 * c) {
	foobar();
}
for (local.i = a; i NEQ b; i += c) {
	/* cfscriptify warning: is NEQ what you wanted? */
	foobar();
}
for (local.i = 1.0; i GTE 0.0; i += -0.1) {
	foobar();
}
