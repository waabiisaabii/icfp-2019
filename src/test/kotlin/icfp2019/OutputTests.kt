package icfp2019

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OutputTests {
    @Test
    fun testOutputOfOneRobot() {
        val actions = mutableListOf<Action>()
        actions.add(Action.MoveDown)
        actions.add(Action.MoveDown)
        actions.add(Action.MoveLeft)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveRight)

        val map = mutableMapOf<RobotId, List<Action>>()
        map.put(RobotId(1), actions)

        val actualOutput = Output.encodeRobotActions(map)

        Assertions.assertEquals("SSAWWD", actualOutput)
    }

    @Test
    fun testOutputOfTwoRobot() {
        val actions = mutableListOf<Action>()
        actions.add(Action.MoveDown)
        actions.add(Action.MoveDown)
        actions.add(Action.MoveLeft)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveRight)

        // Adding two robots
        val map = mutableMapOf<RobotId, List<Action>>()
        map.put(RobotId(1), actions)
        map.put(RobotId(2), actions)

        val actualOutput = Output.encodeRobotActions(map)
        Assertions.assertEquals("SSAWWD#SSAWWD", actualOutput)
    }

    @Test
    fun testOutputOfSolutionWithTeleport() {
        val actions = mutableListOf<Action>()
        actions.add(Action.MoveDown)
        actions.add(Action.MoveDown)
        actions.add(Action.MoveLeft)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveRight)
        actions.add(Action.TeleportBack(Point(1, 2)))

        // Adding two robots
        val map = mutableMapOf<RobotId, List<Action>>()
        map.put(RobotId(1), actions)
        map.put(RobotId(2), actions)

        val actualOutput = Output.encodeRobotActions(map)
        Assertions.assertEquals("SSAWWDT(1,2)#SSAWWDT(1,2)", actualOutput)
    }

    @Test
    fun testOutputOfSolutionWithManipulator() {
        val actions = mutableListOf<Action>()
        actions.add(Action.MoveDown)
        actions.add(Action.MoveDown)
        actions.add(Action.MoveLeft)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveUp)
        actions.add(Action.MoveRight)
        actions.add(Action.AttachManipulator(Point(1, 2)))

        // Adding two robots
        val map = mutableMapOf<RobotId, List<Action>>()
        map.put(RobotId(1), actions)
        map.put(RobotId(2), actions)

        val actualOutput = Output.encodeRobotActions(map)
        Assertions.assertEquals("SSAWWDA(1,2)#SSAWWDA(1,2)", actualOutput)
    }
}
