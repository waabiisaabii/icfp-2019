package icfp2019

data class GameBoard(
    val cells: Array<Short>,
    val width: Int,
    val height: Int
) {
    companion object {
        fun gameBoardOf(problem: Problem): GameBoard {
            val cells = Array<Short>(problem.size.x * problem.size.y) { 0 }

            // TODO: Implement board creation

            return GameBoard(cells, problem.size.x, problem.size.y)
        }
    }

    fun get(x: Int, y: Int): Short {
        return cells[x * width + y]
    }

    fun set(x: Int, y: Int, value: Short): GameBoard {
        val newCells = cells.clone()
        newCells[x * width + y] = value
        return GameBoard(newCells, width, height)
    }
}