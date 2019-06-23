package icfp2019.analyzers

import icfp2019.model.*
import org.junit.jupiter.api.Test
import org.pcollections.TreePVector

class DFSAnalyzerTest {
    @Test
    fun testDfsAnalyzerTest() {
        var gameBoard = GameBoard(
            TreePVector.from(
                listOf(
                    TreePVector.from(
                        listOf(
                            Node(Point(0, 0), false),
                            Node(Point(0, 1), false)
                        )
                    ),
                    TreePVector.from(
                        listOf(
                            Node(Point(1, 0), false),
                            Node(Point(1, 1), false)
                        )
                    ),
                    TreePVector.from(
                        listOf(
                            Node(Point(2, 0), false),
                            Node(Point(2, 1), false)
                        )
                    )
                )
            ), 3, 2
        )

        val robotState = RobotState(RobotId(0), Point(0, 0), Orientation.Up, 0)
        val gameState = GameState(
            gameBoard.cells,
            MapSize(gameBoard.width, gameBoard.height),
            listOf(robotState),
            listOf(),
            listOf())
        val analyzer = DFSAnalyzer.analyze(gameBoard)
        val listOfActions = analyzer.invoke(gameState)
        println(listOfActions)
    }
}
