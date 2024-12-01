subprojects {
    if (this.name.startsWith("year")) {
        this.pluginManager.apply("advent-of-code")
    }
}