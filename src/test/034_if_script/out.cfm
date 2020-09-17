if (form.action EQ "recode") {
	recodeUnit(objUnit);
	deleteUnit(objUnit);
}
else if (form.action eq "delete") {
	deleteUnit(objUnit);
}
