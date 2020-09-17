for (i = 1; i LTE 10; i++) {
	if (i MOD 3) {
		/* fizz */
		continue;
	}
	if (i MOD 5) {
		/* buzz */
		break;
	}
}
