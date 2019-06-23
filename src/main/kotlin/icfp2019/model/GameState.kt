package icfp2019.model

data class GameState(
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {
    companion object {
        // Helper for constructing a game state from minimal description
        fun gameStateOf(startingPosition: Point) =
            GameState(
                listOf(RobotState(RobotId(0), startingPosition)),
                listOf(),
                listOf()
            )

        // Construct an initial game state from the problem spec
        fun gameStateOf(problem: Problem) =
            GameState(
                listOf(RobotState(RobotId(0), problem.startingPosition)),
                listOf(),
                listOf()
            )
    }
}
