package icfp2019

data class GameBoard(
    val problem: Problem,
    val cells: Array<Short>,
    val width: Int,
    val height: Int
) {
    companion object {
        fun gameBoardOf(problem: Problem): GameBoard {
            val cells = Array<Short>(problem.size.x * problem.size.y) { 0 }
            for (column in problem.map) {
                for (cell in column) {
                    var flags: Short = 0
                    if (cell.isObstacle) {
                        flags = Cell.setFlag(flags, Cell.OBSTACLE)
                    } else {
                        flags = when (cell.booster) {
                            Booster.ExtraArm -> Cell.setFlag(flags, Cell.BOOST_EXT)
                            Booster.Drill -> Cell.setFlag(flags, Cell.BOOST_DRILL)
                            Booster.FastWheels -> Cell.setFlag(flags, Cell.BOOST_FAST)
                            Booster.Teleporter -> Cell.setFlag(flags, Cell.BOOST_TELEPORT)
                            Booster.CloneToken -> Cell.setFlag(flags, Cell.BOOST_CLONE)
                            else -> flags
                        }
                    }
                    cells[cell.point.x * problem.size.y + cell.point.y] = flags
                }
            }

            return GameBoard(problem, cells, problem.size.x, problem.size.y)
        }
    }

    fun isInBoard(point: Point): Boolean {
        return (point.x >= 0 && point.x < problem.size.x && point.y >= 0 && point.y < problem.size.y)
    }

    fun get(point: Point): Short {
        if (!isInBoard(point)) {
            throw ArrayIndexOutOfBoundsException("Access out of game board")
        }
        return cells[point.x * height + point.y]
    }

    fun set(point: Point, value: Short): GameBoard {
        if (!isInBoard(point)) {
            throw ArrayIndexOutOfBoundsException("Access out of game board")
        }
        val newCells = cells.clone()
        newCells[point.x * height + point.y] = value
        return GameBoard(problem, newCells, width, height)
    }
}
