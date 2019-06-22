package icfp2019

data class GameState(
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {
    companion object {
        fun empty(startingPoint: Point) =
            GameState(listOf(RobotState(RobotId(0), startingPoint)), listOf(), listOf())
    }
}
