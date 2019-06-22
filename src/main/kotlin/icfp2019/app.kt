package icfp2019

import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val path = Paths.get(if (args.isNotEmpty()) args[0] else "./problems").toAbsolutePath()
    path.toFile().walk().forEach {
        if (it.isFile && it.extension.equals("desc")) {
            println("Running " + it.name)
            val problem = parseDesc(it.readText())
            val solution = solve(problem)

            File(it.parent + "/" + it.nameWithoutExtension + ".sol").writeBytes(solution.toByteArray())
        }
    }
}

fun solve(problem: Problem): String {
    val strategy = BackTrackingStrategy()
    val gameBoardOf = GameBoard.gameBoardOf(problem)
    val gameState = GameState(gameBoardOf, listOf(RobotState(RobotId(0), problem.startingPosition)), listOf(), listOf())
    val actions = strategy.getActions(gameState)
    return Output.encodeRobotActions(actions)
}

fun constructObstacleMap(problem: Problem): Array<Array<Boolean>> {
    return problem.map.map { it.map { it.isObstacle }.toTypedArray() }.toTypedArray()
}
