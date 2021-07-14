rootProject.name = "restrictionhelper"

projects("core", "bukkit")

fun projects(vararg names: String) {
    include(*names)

    names.forEach {
        project(":$it").name = "restrictionhelper-$it"
    }
}
