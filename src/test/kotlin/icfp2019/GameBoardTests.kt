package icfp2019
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameBoardTests {
    @Test
    fun testGameBoardRowsSequence() {
        var g = GameBoard(arrayOf(1, 2, 3, 4, 5, 6), 3, 2)
        g = g.set(Point(0, 0), 1)
        g = g.set(Point(1, 0), 2)
        g = g.set(Point(2, 0), 3)
        g = g.set(Point(0, 1), 4)
        g = g.set(Point(1, 1), 5)
        g = g.set(Point(2, 1), 6)
        val rows = g.rows()
        Assertions.assertEquals(2, rows.size)
        Assertions.assertEquals(3, rows[0].size)
        Assertions.assertEquals(3, rows[1].size)
        Assertions.assertEquals(g.get(Point(0, 0)), 1)
        Assertions.assertEquals(g.get(Point(1, 0)), 2)
        Assertions.assertEquals(g.get(Point(2, 0)), 3)
        Assertions.assertEquals(1, rows[0][0])
        Assertions.assertEquals(2, rows[0][1])
        Assertions.assertEquals(3, rows[0][2])
        Assertions.assertEquals(4, rows[1][0])
        Assertions.assertEquals(5, rows[1][1])
        Assertions.assertEquals(6, rows[1][2])
    }
}
