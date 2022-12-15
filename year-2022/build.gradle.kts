plugins {
    java
}

dependencies {
    implementation("com.github.ajalt:mordant:1.2.1")
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/kotlin")
            includes.add("**/*.in")
        }
    }
    test {
        resources {
            srcDirs("src/test/kotlin")
            includes.add("**/*.in")
        }
    }
}

tasks.test {
    outputs.upToDateWhen { false }
    testLogging {
        // Make sure output from
        // standard out or error is shown
        // in Gradle output.
        showStandardStreams = true
    }
}