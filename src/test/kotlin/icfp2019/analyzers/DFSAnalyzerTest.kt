package icfp2019.analyzers

import icfp2019.model.Action
import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.toProblem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DFSAnalyzerTest {
    @Test
    fun `get rid of being stuck`() {
        val problem = """
                            XXXXXXXX
                            XXXXwX.X
                            XXXXwX.X
                            Xw@wwX.X
                            XXXX...X
                            XXXXXXXX
                        """.toProblem()

        val gameBoard = GameBoard(problem)
        val startingState = GameState
            .gameStateOf(problem)

        val actions = DFSAnalyzer
            .analyze(gameBoard)
            .invoke(startingState)

        Assertions.assertEquals(
            Action.MoveRight,
            actions.first()
        )
    }

    @Test
    fun `get rid of being stuck 3`() {
        val problem = """
                            XXXXXXXX
                            XXXXwX.X
                            XXXXwX.X
                            Xww@wX.X
                            XXXX...X
                            XXXXXXXX
                        """.toProblem()

        val gameBoard = GameBoard(problem)
        val startingState = GameState
            .gameStateOf(problem)

        val actions = DFSAnalyzer
            .analyze(gameBoard)
            .invoke(startingState)

        Assertions.assertEquals(
            Action.MoveRight,
            actions.first()
        )
    }

    @Test
    fun `verify dfs movement`() {
        val problem = """
                            XXXXXXXX
                            XXXXwX.X
                            XXXXwX.X
                            Xwww@X.X
                            XXXX...X
                            XXXXXXXX
                        """.toProblem()

        val gameBoard = GameBoard(problem)
        val startingState = GameState
            .gameStateOf(problem)

        val actions = DFSAnalyzer
            .analyze(gameBoard)
            .invoke(startingState)

        Assertions.assertEquals(
            Action.MoveDown,
            actions.first()
        )
    }
}