package icfp2019

import com.google.common.base.CharMatcher
import com.google.common.base.Splitter
import icfp2019.model.MapSize
import icfp2019.model.Node
import icfp2019.model.Point
import icfp2019.model.Problem
import org.pcollections.TreePVector
import java.nio.file.Paths

fun loadProblem(problemNumber: Int): String {
    val path = Paths.get("problems/prob-${problemNumber.toString().padStart(3, padChar = '0')}.desc").toAbsolutePath()
    return path.toFile().readText()
}

fun boardString(p: Problem, path: Set<Node> = setOf()): String {
    val lines = mutableListOf<String>()
    for (y in (p.size.y - 1) downTo 0) {

        val row = (0 until p.size.x).map { x ->
            val node = p.map[x][y]
            when {
                p.startingPosition == Point(x, y) -> '@'
                node.isObstacle -> 'X'
                node in path -> '|'
                node.isWrapped -> 'w'
                node.booster != null -> 'o'
                else -> '.'
            }
        }.joinToString(separator = " ")
        lines.add(row)
    }
    return lines.joinToString(separator = "\n")
}

fun printBoard(p: Problem, path: Set<Node> = setOf()) {
    println("${p.size}")
    print(boardString(p, path))
    println()
}

fun parseTestMap(map: String): Problem {
    val mapLineSplitter = Splitter.on(CharMatcher.anyOf("\r\n")).omitEmptyStrings()
    val lines = mapLineSplitter.splitToList(map).map { CharMatcher.whitespace().removeFrom(it) }.reversed()
    val height = lines.size
    val width = lines[0].length
    if (lines.any { it.length != width }) throw IllegalArgumentException("Inconsistent map line lengths")
    val startPoint =
        (0 until width).map { x ->
            (0 until height).map { y ->
                if (lines[y][x] == '@') Point(x, y)
                else null
            }
        }.flatten().find { it != null } ?: Point.origin()
    return Problem(MapSize(width, height), startPoint, TreePVector.from((0 until width).map { y ->
        TreePVector.from((0 until height).map { x ->
            val point = Point(x, y)
            when (lines[x][y]) {
                'X' -> Node(point, isObstacle = true)
                'w' -> Node(point, isObstacle = false, isWrapped = true)
                '.' -> Node(point, isObstacle = false)
                '@' -> Node(point, isObstacle = false)
                else -> throw IllegalArgumentException("Unknown Char '${lines[x][y]}'")
            }
        })
    }))
}
