package icfp2019

import java.nio.file.Paths

fun loadProblem(problemNumber: Int): String {
    val path = Paths.get("problems/prob-${problemNumber.toString().padStart(3, padChar = '0')}.desc").toAbsolutePath()
    return path.toFile().readText()
}

fun boardString(p: Problem): String {
    val lines = mutableListOf<String>()
    for (y in (p.size.y - 1) downTo 0) {

        val row = (0 until p.size.x).map { x ->
            if (p.startingPosition == Point(x, y)) 's' else {
                val node = p.map[x][y]
                if (node.isObstacle) 'X' else {
                    if (node.booster != null) 'o' else '.'
                }
            }
        }.joinToString(separator = " ")
        lines.add(row)
    }
    return lines.joinToString(separator = "\n")
}

fun printBoard(p: Problem) {
    println("${p.size}")
    print(boardString(p))
}
