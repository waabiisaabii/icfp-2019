package icfp2019.model

import icfp2019.core.applyAction
import icfp2019.toProblem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class RobotStateTest {
    @Test
    fun testRotate() {

        val map = """..""".toProblem()
        val startState = GameState(map)

        val state0 = applyAction(startState, RobotId(0), Action.TurnClockwise)
        Assertions.assertEquals(
            listOf(Point(0, 1), Point(-1, 1), Point(1, 1)),
            state0.robot(RobotId(0)).armRelativePoints
        )

        val state1 = applyAction(startState, RobotId(0), Action.TurnCounterClockwise)
        Assertions.assertEquals(
            listOf(Point(0, -1), Point(1, -1), Point(-1, -1)),
            state1.robot(RobotId(0)).armRelativePoints
        )
    }

    @Test
    fun testRotate360() {

        val map = """..""".toProblem()
        val startState = GameState(map)

        val state0 = applyAction(startState, RobotId(0), Action.TurnClockwise)
        val state1 = applyAction(state0, RobotId(0), Action.TurnClockwise)
        val state2 = applyAction(state1, RobotId(0), Action.TurnClockwise)
        val finalState = applyAction(state2, RobotId(0), Action.TurnClockwise)

        Assertions.assertEquals(
            listOf(Point(1, 0), Point(1, 1), Point(1, -1)),
            finalState.robot(RobotId(0)).armRelativePoints
        )
    }
}