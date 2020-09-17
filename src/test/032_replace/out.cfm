public string function getSpecialTableName() {
	if (this.isTT()) {
		return this.specialTableName;
	}
	else {
		return Replace(this.specialTableName, '_tt_', '_ntt_');
	}
}
