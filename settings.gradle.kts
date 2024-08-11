rootProject.name = "restrictionhelper"

projects("core", "spigot")

fun projects(vararg names: String) {
	include(*names)

	names.forEach {
		project(":$it").name = "restrictionhelper-$it"
	}
}
