package icfp2019

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val workingDir: Path = Paths.get("")

    val solutions = mutableListOf<Solution>()
    readZipFile(File("problems.zip"))
        .filter { it.line.isNotEmpty() }
        .forEach {
            val problem = parseDesc(it)
            val solution = solve(problem)
            encodeSolution(solution, workingDir)
        }

    writeZip(workingDir, solutions)
}

fun writeZip(workingDir: Path, solutions: MutableList<Solution>) {
    TODO(workingDir.toString() + solutions.toString() + "not implemented")
}

fun readZipFile(file: File): List<ProblemDescription> {
    TODO(file.toString() + "not implemented")
}

enum class Boosters {
    B, F, L, X
}

data class Point(val x: Int, val y: Int)
data class Node(val point: Point, val isObstacle: Boolean, val booster: Boosters? = null)
data class ProblemId(val id: Int)
data class ProblemDescription(val problemId: ProblemId, val line: String)
data class Size(val x: Int, val y: Int)
data class Problem(
    val problemId: ProblemId,
    val size: Size,
    val startingPosition: Point,
    val map: Array<Array<Node>>
)

/*
Task:
 1. Open Zip file
 2. parse a problem at a time: prob_NNN.desc
 3. solve problem
 4. encode solution
 5. output to file prob_NNN.sol (use checker to validate?) https://icfpcontest2019.github.io/solution_checker/
 6. add solution to another zip (script/program)
 */

enum class Actions {
    W, S, A, D, Z, E, Q, B, F, L
}

data class RobotState(
    val robotId: RobotId,
    val currentPosition: Point,
    val orientation: Orientation = Orientation.Up,
    val remainingFastWheelTime: Int? = null,
    val remainingDrillTime: Int? = null
)

data class RobotId(val id: Int)

enum class Orientation {
    Up, Down, Left, Right
}

data class Solution(val problemId: ProblemId, val actions: List<Actions>)

fun solve(problem: Problem): Solution {
    return Solution(problem.problemId, listOf())
}

fun constructObstacleMap(problem: Problem): Array<Array<Boolean>> {
    val rowSize = problem.map.size
    val colSize = problem.map.get(0).size
    // Create a Array of Array map for the given problem with
    val rowObstacle = Array(rowSize) { Array(colSize) { false } }
    val row = problem.map
    for (i in row.indices) {
        val colObstacle = Array(colSize) { false }
        val col = row[i]
        for (j in col.indices) {
            val node = col[j]
            if (node.isObstacle) {
                colObstacle[j] = true
            }
        }
        rowObstacle[i] = colObstacle
    }
    return rowObstacle
}

fun encodeSolution(solution: Solution, directory: Path): File {
    val file = Files.createFile(directory.resolve("prob-${solution.problemId.id}.sol"))
    // TODO
    return file.toFile()
}
