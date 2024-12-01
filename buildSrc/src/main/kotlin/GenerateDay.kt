import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Files

open class GenerateDay : DefaultTask() {

    @Input
    var day: String = "99"

    fun File.whenNotExist(action: (File) -> Unit) {
        if (!this.exists()) action.invoke(this)
    }

    @TaskAction
    fun generate() {
        val year = project.name.replace("year-", "")
        println("Generating sources for problem y$year-d$day")

        val templateDir = project.rootProject.projectDir.toPath()
        val dayTemplate = templateDir.resolve("templates/DayDD.kt").toFile().readText()
        val testDayTemplate = templateDir.resolve("templates/DayDDTest.kt").toFile().readText()

        val daySrcDir = project.projectDir.toPath().resolve("src/main/kotlin/mri/advent/y$year/day$day")
        Files.createDirectories(daySrcDir)
        val inputDir = project.projectDir.toPath().resolve("src/main/resources/input")
        Files.createDirectories(inputDir)

        // main code
        daySrcDir.resolve("Day${day}.kt").toFile().whenNotExist {
            it.writeText(dayTemplate.replace("DD", day).replace("YYYY", year))
            println("created main source file $it")
        }
        // main input data file
        inputDir.resolve("day${day}.txt").toFile().whenNotExist { inputFile ->
            val cookieFile = project.rootProject.file("/private/session-cookie.txt")
            if (cookieFile.exists()) {
                println("Input data file not found, try downloading it..")
                val url = "https://adventofcode.com/$year/day/${day.toInt()}/input"
                getInputData(url, project.rootProject.file("/private/session-cookie.txt").readText())?.let {
                    val lines = it.lines()
                    inputFile.writeText(lines.dropLast(if (lines.last().isBlank()) 1 else 0).joinToString("\n"))
                    println("Input data file downloaded OK")
                } ?: run {
                    println("could not get input data file.")
                    inputFile.createNewFile()
                }
            } else {
                println("No session cookie found, create empty file")
                inputFile.createNewFile()
            }
        }

        // test code
        daySrcDir.resolve("Day${day}Test.kt").toFile().whenNotExist {
            it.writeText(testDayTemplate.replace("DD", day).replace("YYYY", year))
            println("created test source file $it")
        }
    }

    private fun getInputData(url: String, cookie: String): String? {
        val request = Request.Builder().url(url).addHeader("Cookie", cookie).build()
        try {
            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    System.err.println("Error during download input data: $response")
                    return null
                }
                return response.body?.string()
            }
        } catch (ex: Exception) {
            println("error during download: ${ex.message}")
            return null
        }
    }

}