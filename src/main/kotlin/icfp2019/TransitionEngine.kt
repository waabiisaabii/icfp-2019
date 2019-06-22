package icfp2019

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

class TransitionEngine {
    fun apply(gameState: GameState, actions: Map<RobotId, Action>): GameState {
        var currentGameState = gameState
        actions.forEach { x ->
            currentGameState = when (x.value) {
                MoveDown -> applyMoveDown(x.key, currentGameState)
                MoveUp -> applyMoveUp(x.key, currentGameState)
                MoveLeft -> applyMoveLeft(x.key, currentGameState)
                MoveRight -> applyMoveRight(x.key, currentGameState)
                else -> throw RuntimeException("Not Impl")
            }
        }
        return currentGameState
    }

    fun applyMoveDown(robotId: RobotId, gameState: GameState): GameState {
        val robotState = gameState.robotStateList.get(robotId.id)
        val newY = robotState.currentPosition.y - 1
        if (newY < 0) throw IllegalArgumentException("Moved off board!")
        val newRobotState = RobotState(
            robotId,
            Point(robotState.currentPosition.x, newY))

        return GameState(gameState.gameBoard, listOf(newRobotState), listOf(), listOf())
    }

    fun applyMoveUp(robotId: RobotId, gameState: GameState): GameState {
        val robotState = gameState.robotStateList.get(robotId.id)
        val newY = robotState.currentPosition.y + 1
        if (newY > gameState.gameBoard.height - 1) throw IllegalArgumentException("Moved off board!")
        val newRobotState = RobotState(
            robotId,
            Point(robotState.currentPosition.x, newY))

        return GameState(gameState.gameBoard, listOf(newRobotState), listOf(), listOf())
    }

    fun applyMoveLeft(robotId: RobotId, gameState: GameState): GameState {
        val robotState = gameState.robotStateList.get(robotId.id)
        val newX = robotState.currentPosition.x - 1
        if (newX < 0) throw IllegalArgumentException("Moved off board!")
        val newRobotState = RobotState(
            robotId,
            Point(newX, robotState.currentPosition.y))

        return GameState(gameState.gameBoard, listOf(newRobotState), listOf(), listOf())
    }

    fun applyMoveRight(robotId: RobotId, gameState: GameState): GameState {
        val robotState = gameState.robotStateList.get(robotId.id)
        val newX = robotState.currentPosition.x + 1
        if (newX < gameState.gameBoard.width - 1) throw IllegalArgumentException("Moved off board!")
        val newRobotState = RobotState(
            robotId,
            Point(newX, robotState.currentPosition.x))

        return GameState(gameState.gameBoard, listOf(newRobotState), listOf(), listOf())
    }
}
