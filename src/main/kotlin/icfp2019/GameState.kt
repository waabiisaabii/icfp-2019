package icfp2019

data class GameState(
    val gameBoard: GameBoard,
    val robotStateList: List<RobotState>,
    val teleportDestination: List<Point>,
    val unusedBoosters: List<Booster>
) {

    fun availableMoves(point: Point): List<Action> {
        val moves = mutableListOf<Action>()

        if (point.y + 1 < gameBoard.height) {
            val up = gameBoard.get(point.x, point.y + 1)
            if (!Cell.hasFlag(up, Cell.WRAPPED) && !Cell.hasFlag(up, Cell.OBSTACLE)) {
                moves.add(Action.MoveUp)
            }
        }

        if (point.y - 1 > -1) {
            val down = gameBoard.get(point.x, point.y - 1)
            if (!Cell.hasFlag(down, Cell.WRAPPED) && !Cell.hasFlag(down, Cell.OBSTACLE)) {
                moves.add(Action.MoveDown)
            }
        }

        if (point.x - 1 > -1) {
            val left = gameBoard.get(point.x - 1, point.y)
            if (!Cell.hasFlag(left, Cell.WRAPPED) && !Cell.hasFlag(left, Cell.OBSTACLE)) {
                moves.add(Action.MoveLeft)
            }
        }

        if (point.x + 1 < gameBoard.width) {
            val right = gameBoard.get(point.x + 1, point.y)
            if (!Cell.hasFlag(right, Cell.WRAPPED) && !Cell.hasFlag(right, Cell.OBSTACLE)) {
                moves.add(Action.MoveRight)
            }
        }

        return moves
    }
}
