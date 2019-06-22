package icfp2019

import java.io.File

fun main(args: Array<String>) {
    File(args[0]).walk().forEach {
        if (it.isFile && it.extension.equals("desc")) {
            println("Running " + it.name)
            val problem = parseDesc(it.readText())
            val solution = solve(problem)
            File(it.nameWithoutExtension + ".sol").writeBytes(solution.toString().toByteArray())
        }
    }
}

fun solve(problem: Problem): String {
//    return Solution(problem)
    println(problem)
    return "Foo"
}

fun constructObstacleMap(problem: Problem): Array<Array<Boolean>> {
    return problem.map.map { it.map { it.isObstacle }.toTypedArray() }.toTypedArray()
}