package icfp2019.model

import org.pcollections.PVector
import org.pcollections.TreePVector

data class GameState(
    val cells: PVector<PVector<Node>>,
    val mapSize: MapSize,
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {
    companion object {
        // Helper for constructing a game state from minimal description
        fun gameStateOf(startingPosition: Point) =
            GameState(
                TreePVector.empty(),
                MapSize(0, 0),
                listOf(RobotState(RobotId(0), startingPosition)),
                listOf(),
                listOf()
            )

        // Construct an initial game state from the problem spec
        fun gameStateOf(problem: Problem) =
            GameState(
                problem.map,
                problem.size,
                listOf(RobotState(RobotId(0), problem.startingPosition)),
                listOf(),
                listOf()
            )

        fun gameStateOf(problem: Problem, startingPosition: Point) =
            GameState(
                problem.map,
                problem.size,
                listOf(RobotState(RobotId(0), startingPosition)),
                listOf(),
                listOf()
            )
    }

    fun isInBoard(point: Point): Boolean {
        return (point.x in 0 until mapSize.x && point.y in 0 until mapSize.y)
    }

    fun get(point: Point): Node {
        if (!isInBoard(point)) {
            throw ArrayIndexOutOfBoundsException("Access out of game board")
        }
        return cells[point.x][point.y]
    }

    fun set(point: Point, value: Node): GameState {
        if (!isInBoard(point)) {
            throw ArrayIndexOutOfBoundsException("Access out of game board")
        }
        val newCells = cells.with(point.x, cells[point.x].with(point.y, value))
        return GameState(newCells, mapSize, robotStateList, teleportDestination, unusedBoosters)
    }
}
