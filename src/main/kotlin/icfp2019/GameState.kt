package icfp2019

data class GameState(
    val gameBoard: GameBoard,
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
)
