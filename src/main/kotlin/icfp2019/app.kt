package icfp2019

import icfp2019.analyzers.DFSAnalyzer
import icfp2019.model.*
import icfp2019.model.GameState.Companion.gameStateOf
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val path = Paths.get(if (args.isNotEmpty()) args[0] else "./problems").toAbsolutePath()
    path.toFile().walk().forEach {
        if (it.isFile && it.extension.equals("desc")) {
            println("Running " + it.name)
            val problem = parseDesc(it.readText())
            val solution = solve(problem)
            File(it.parent, "${it.nameWithoutExtension}.sol").writeBytes(solution.toByteArray())
        }
    }
}

fun solve(problem: Problem): String {
    val gameBoard = GameBoard(problem)
    val gameState = gameStateOf(problem)
    val actions = DFSAnalyzer.analyze(gameBoard).invoke(gameState)
    return mapOf(Pair(RobotId(0), actions)).encodeActions()
}

fun constructObstacleMap(problem: Problem): Array<Array<Boolean>> {
    return problem.map.map { it.map { it.isObstacle }.toTypedArray() }.toTypedArray()
}
