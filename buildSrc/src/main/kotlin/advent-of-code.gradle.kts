plugins {
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    add("implementation", kotlin("stdlib"))
    add("implementation", "org.junit.jupiter:junit-jupiter-api:5.8.1")
    add("implementation", "org.junit.jupiter:junit-jupiter-engine:5.8.1")
    add("runtimeOnly", "org.junit.platform:junit-platform-launcher")
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/kotlin")
            includes.add("**/*.txt")
        }
    }
}
// create day generation tasks
(1..25).map { it.toString().padStart(2, '0') }.forEach { day ->
    tasks.register("day-$day", GenerateDay::class.java) {
        group = "adventofcode"
        this.day = day
    }
}
