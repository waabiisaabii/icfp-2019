package icfp2019.analyzers

import icfp2019.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.pcollections.TreePVector

class MSTAnalyzerTest {

    @Test
    fun testMinimumSpanningTree6Nodes() {
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

        val spanningTree = MSTAnalyzer
            .analyze(gameBoard)(GameState.gameStateOf(Point(2, 1)))

        val count = spanningTree.edges.size
        Assertions.assertEquals(count, 5)
    }

    @Test
    fun testMinimumSpanningTree9Nodes() {
        var gameBoard = GameBoard(
            TreePVector.from(
                listOf(
                    TreePVector.from(
                        listOf(
                            Node(Point(0, 0), false),
                            Node(Point(0, 1), false),
                            Node(Point(0, 2), false)
                        )
                    ),
                    TreePVector.from(
                        listOf(
                            Node(Point(1, 0), false),
                            Node(Point(1, 1), false),
                            Node(Point(1, 2), false)
                        )
                    ),
                    TreePVector.from(
                        listOf(
                            Node(Point(2, 0), false),
                            Node(Point(2, 1), false),
                            Node(Point(2, 2), false)
                        )
                    )
                )
            ), 3, 3
        )

        val spanningTree = MSTAnalyzer
            .analyze(gameBoard)(GameState.gameStateOf(Point(0, 0)))

        val count = spanningTree.edges.size
        Assertions.assertEquals(count, 8)
    }
}