rootProject.name = "advent-of-code"

rootProject.projectDir
    .listFiles { file -> file.isDirectory && file.name.startsWith("year-") }
    ?.forEach { include(it.name) }

