import java.nio.file.Files

plugins {
    kotlin("jvm") version "1.7.21" apply false
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories {
        mavenCentral()
    }
    val implementation by configurations
    val testImplementation by configurations
    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation(kotlin("test"))
    }
    tasks.withType(Test::class) {
        useJUnitPlatform()
    }


    // Create tasks to generate resources per day
    val year = this.project.name.replace("year-", "")
    val srcDir = this.projectDir.toPath().resolve("src/main/kotlin/mri/advent/y$year")
    val testSrcDir = this.projectDir.toPath().resolve("src/test/kotlin/mri/advent/y$year")
    val dayTemplate = this.rootProject.projectDir.toPath().resolve("templates/DayDD.kt").toFile().readText()
    val testDayTemplate = this.rootProject.projectDir.toPath().resolve("templates/DayDDTest.kt").toFile().readText()
    (1..25).map { if (it < 10) "0$it" else "$it" }.forEach { day ->
        this.tasks.create("gen-day-$day") {
            group = "adventofcode"
            doLast {
                println("generate day $day")
                val daySrcDir = srcDir.resolve("day$day")
                val dayTestSrcDir = testSrcDir.resolve("day$day")
                if (Files.exists(daySrcDir) || Files.exists(dayTestSrcDir)) {
                    println("Day directories already exists - do nothing")
                } else {
                    Files.createDirectories(daySrcDir)
                    daySrcDir.resolve("day${day}.in").toFile().createNewFile()
                    daySrcDir.resolve("Day${day}.kt").toFile().writeText(dayTemplate.replace("DD", day).replace("YYYY", year))
                    Files.createDirectories(dayTestSrcDir)
                    dayTestSrcDir.resolve("Day${day}Test.kt").toFile().writeText(testDayTemplate.replace("DD", day).replace("YYYY", year))
                    dayTestSrcDir.resolve("day${day}_sample.in").toFile().createNewFile()
                }
            }
        }
    }
}