package icfp2019.model

import icfp2019.toProblem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.Math.PI

internal class RobotStateTest {
    @Test
    fun testRotate() {

        val map = """..""".toProblem()
        val startState = GameState(map)

        val point1 = Point(1, 1)
        Assertions.assertEquals(
            Point(1, -1),
            startState.robot(RobotId(0)).rotatePoint(point1, -PI/2))

        val point2 = Point(-1, 1)
        Assertions.assertEquals(
            point1,
            startState.robot(RobotId(0)).rotatePoint(point2, -PI/2))
    }
}