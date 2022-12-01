plugins {
    java
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