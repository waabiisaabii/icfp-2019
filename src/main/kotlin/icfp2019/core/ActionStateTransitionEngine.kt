package icfp2019.core

import icfp2019.model.Action
import icfp2019.model.GameState
import icfp2019.model.RobotId
import icfp2019.model.RobotState

fun applyAction(gameState: GameState, robotId: RobotId, action: Action): GameState {
    val newRobotState = adjustRobotState(gameState, robotId, action)
    return gameState.copy(robotState = gameState.robotState.plus(robotId to newRobotState))
}

private fun adjustRobotState(
    gameState: GameState,
    robotId: RobotId,
    action: Action
): RobotState {
    val robotState = gameState.robotState[robotId] ?: throw IllegalStateException("Robot not found: $robotId")
    return when (action) {
        Action.MoveUp -> robotState.copy(currentPosition = robotState.currentPosition.up())
        Action.MoveDown -> robotState.copy(currentPosition = robotState.currentPosition.down())
        Action.MoveLeft -> robotState.copy(currentPosition = robotState.currentPosition.left())
        Action.MoveRight -> robotState.copy(currentPosition = robotState.currentPosition.right())
        else -> throw UnsupportedOperationException("Unhandled Action")
    }
}
