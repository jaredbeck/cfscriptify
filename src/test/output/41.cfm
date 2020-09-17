try {
	Throw(message="a");
}
catch(any cfcatch) {
	try {
		Throw(message="b");
	}
	catch(any cfcatch) {
		/* 1 */
	}
	finally {
		/* 2 */
	}
}
finally {
	/* 3 */
}
