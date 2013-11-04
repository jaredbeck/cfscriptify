lock timeout="1" {
	Sleep(Rand('SHA1PRNG') * 2000);
}
lock timeout="#Rand('SHA1PRNG') * 1000#" {
	Sleep(500);
}
lock name="x" type="exclusive" timeout="1" {
	lock name="y" type="exclusive" timeout="1" {
	}
}
lock scope="Application" type="readOnly" timeout="1" {
}
