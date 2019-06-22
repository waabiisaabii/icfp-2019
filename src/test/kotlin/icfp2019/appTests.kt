package icfp2019

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.pcollections.PVector
import org.pcollections.TreePVector

class appTests {
    @Test
    fun testObstacleMap() {

        val nodeMap = TreePVector.from(
            listOf<PVector<Node>>(
                TreePVector.from(
                    listOf(
                        Node(Point(0, 0), false, null),
                        Node(Point(0, 1), false, null),
                        Node(Point(0, 2), true, null),
                        Node(Point(0, 3), false, null)
                    )
                ),

                TreePVector.from(
                    listOf(
                        Node(Point(1, 0), true, null),
                        Node(Point(1, 1), false, null),
                        Node(Point(1, 2), true, null),
                        Node(Point(1, 3), false, null)
                    )
                )
            )
        )

        val problem = Problem(MapSize(2, 4), Point(0, 0), nodeMap)
        val obstacle = constructObstacleMap(problem)

        val expected = arrayOf(
            arrayOf(false, false, true, false),
            arrayOf(true, false, true, false)
        )

        Assertions.assertArrayEquals(obstacle, expected)
    }
}
