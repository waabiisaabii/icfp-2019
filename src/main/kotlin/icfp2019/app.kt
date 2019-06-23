package icfp2019

import icfp2019.analyzers.DFSAnalyzer
import icfp2019.model.*
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val path = Paths.get(if (args.isNotEmpty()) args[0] else "./problems").toAbsolutePath()
    path.toFile().walk().forEach {
        if (it.isFile && it.extension.equals("desc")) {
            println("Running " + it.name)
            val problem = parseDesc(it.readText())
            val solutions = solve(problem)
            solutions.forEach { solution ->
                File(it.parent, "${it.nameWithoutExtension}.sol").writeBytes(solution.toByteArray())
            }
        }
    }
}

fun solve(problem: Problem): List<String> {
    val gameState = GameState.gameStateOf(problem)
    val solutions = mutableListOf<String>()

    gameState.robotState.forEach { (robotId, _) ->
        val actions = DFSAnalyzer.analyze(gameState).invoke(robotId, gameState)
        solutions.add(mapOf(Pair(robotId, actions)).encodeActions())
    }

    return solutions
}

fun constructObstacleMap(problem: Problem): Array<Array<Boolean>> {
    return problem.map.map { it.map { it.isObstacle }.toTypedArray() }.toTypedArray()
}
