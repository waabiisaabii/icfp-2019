package icfp2019

data class GameBoard(
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

            return GameBoard(cells, problem.size.x, problem.size.y)
        }
    }

    fun get(x: Int, y: Int): Short {
        return cells[x * height + y]
    }

    fun set(x: Int, y: Int, value: Short): GameBoard {
        val newCells = cells.clone()
        newCells[x * height + y] = value
        return GameBoard(newCells, width, height)
    }
}
