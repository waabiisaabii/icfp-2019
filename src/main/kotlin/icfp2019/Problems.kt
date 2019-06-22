package icfp2019

import java.io.File
import java.util.zip.ZipFile

class Problems {
    private var path: String = ""
    val problemMap = mutableMapOf("" to "")

    fun init(path: String) {
        this.path = path
        run()
    }

    fun run() {
        ZipFile(path).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                if (entry.name.endsWith(".desc")) {
                    val content = File(entry.name).readText()
                    problemMap.put(entry.name, content)
                }
            }
        }
    }

    fun getProblem(id: String): List<String> {
        var content: String = problemMap.getOrDefault(id, "")
        return content.split("#")
    }
}
