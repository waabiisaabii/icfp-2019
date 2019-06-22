package icfp2019

data class GameState(
    val gameBoard: GameBoard,
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {

    fun availableMoves(robot: RobotState): List<Action> {
        val moves = mutableListOf<Action>()
        val cell = gameBoard.get(robot.currentPosition)

        // Asking about a bad location... No moves
        if (!gameBoard.isInBoard(robot.currentPosition)) {
            return moves
        }

        // Things independent of point or location
        moves.add(Action.DoNothing)
        moves.add(Action.TurnClockwise)
        moves.add(Action.TurnCounterClockwise)
        moves.add(Action.PlantTeleportResetPoint)
        for (location: Point in teleportDestination) {
            moves.add(Action.TeleportBack(location))
        }

        fun canMoveTo(point: Point): Boolean {
            return gameBoard.isInBoard(point) && !cell.isObstacle
        }

        // Check directions
        if (canMoveTo(robot.currentPosition.up())) {
            moves.add(Action.MoveUp)
        }

        if (canMoveTo(robot.currentPosition.down())) {
            moves.add(Action.MoveDown)
        }

        if (canMoveTo(robot.currentPosition.right())) {
            moves.add(Action.MoveRight)
        }

        if (canMoveTo(robot.currentPosition.left())) {
            moves.add(Action.MoveLeft)
        }

        // Are we on a clone point?
        if (cell.hasBooster(Booster.CloningLocation)) {
            moves.add(Action.CloneRobot)
        }

        return moves
    }
}
