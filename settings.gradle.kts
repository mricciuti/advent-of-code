rootProject.name = "advent-of-code"

listOf("year-2022", "year-2021", "year-2020", "report", "gui").forEach {
    if (file(it).exists()) {
        include(it)
    }
}

