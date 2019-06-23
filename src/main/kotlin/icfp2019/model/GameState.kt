package icfp2019.model

data class GameState(
    val robotState: RobotState, // For now just one robot -- eventually there will be more
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {
    companion object {
        fun empty(startingPoint: Point) =
            GameState(
                RobotState(RobotId(0), startingPoint),
                listOf(RobotState(RobotId(0), startingPoint)),
                listOf(),
                listOf()
            )
    }
}
