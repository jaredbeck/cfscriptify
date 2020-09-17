try {
	Throw(message="kaboom");
}
catch(database cfcatch) {
	foo = cfcatch.message;
	rethrow;
}
catch(any cfcatch) {
	abort;
}
finally {
	foo = "bar";
}
