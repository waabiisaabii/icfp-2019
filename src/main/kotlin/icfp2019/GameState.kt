package icfp2019

data class GameState(
    val gameBoard: GameBoard,
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {

    fun availableMoves(point: Point): List<Action> {
        val moves = mutableListOf<Action>()
        val cell = gameBoard.get(point)

        // Asking about a bad location... No moves
        if (!gameBoard.isInBoard(point)) {
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

        // Check directions
        if (gameBoard.isInBoard(Point(point.x, point.y + 1)) &&
                !Cell.hasFlag(cell, Cell.OBSTACLE)) {
            moves.add(Action.MoveUp)
        }
        if (gameBoard.isInBoard(Point(point.x, point.y - 1)) &&
            !Cell.hasFlag(cell, Cell.OBSTACLE)) {
            moves.add(Action.MoveDown)
        }
        if (gameBoard.isInBoard(Point(point.x + 1, point.y)) &&
            !Cell.hasFlag(cell, Cell.OBSTACLE)) {
            moves.add(Action.MoveRight)
        }
        if (gameBoard.isInBoard(Point(point.x - 1, point.y)) &&
            !Cell.hasFlag(cell, Cell.OBSTACLE)) {
            moves.add(Action.MoveRight)
        }

        // Check Boosters
        if (Cell.hasFlag(cell, Cell.BOOST_CLONE) ||
            Cell.hasFlag(cell, Cell.BOOST_TELEPORT) ||
            Cell.hasFlag(cell, Cell.BOOST_FAST) ||
            Cell.hasFlag(cell, Cell.BOOST_DRILL) ||
            Cell.hasFlag(cell, Cell.BOOST_EXT)) {
            moves.add(Action.AttachManipulator(point))
        }

        // Are we on a clone point?
        if (Cell.hasFlag(cell, Cell.SPAWN_POINT)) {
            moves.add(Action.CloneRobot)
        }

        return moves
    }
}
