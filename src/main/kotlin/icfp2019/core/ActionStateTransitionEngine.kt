package icfp2019.core

import icfp2019.model.*
import org.pcollections.PVector

fun applyAction(gameState: GameState, robotId: RobotId, action: Action): GameState {
    val newRobotState = adjustRobotState(gameState, robotId, action)
    return gameState.copy(robotState = gameState.robotState.plus(robotId to newRobotState)).let {
        it.copy(cells = wrapAffectedCells(it.cells, newRobotState))
    }
}

fun PVector<PVector<Node>>.get(point: Point): Node {
    return this[point.x][point.y]
}

fun PVector<PVector<Node>>.update(point: Point, node: Node): PVector<PVector<Node>> {
    return this.with(point.x, this[point.x].with(point.y, node))
}

fun wrapAffectedCells(cells: PVector<PVector<Node>>, newRobotState: RobotState): PVector<PVector<Node>> {
    val pos = newRobotState.currentPosition
    return cells.update(pos, cells.get(pos).copy(isWrapped = true))
}

private fun adjustRobotState(
    gameState: GameState,
    robotId: RobotId,
    action: Action
): RobotState {
    val robotState = gameState.robotState[robotId] ?: throw IllegalStateException("Robot not found in game state: $robotId")
    return when (action) {
        Action.Initialize -> robotState // Game initialize
        Action.MoveUp -> robotState.copy(currentPosition = robotState.currentPosition.up())
        Action.MoveDown -> robotState.copy(currentPosition = robotState.currentPosition.down())
        Action.MoveLeft -> robotState.copy(currentPosition = robotState.currentPosition.left())
        Action.MoveRight -> robotState.copy(currentPosition = robotState.currentPosition.right())
        else -> throw UnsupportedOperationException("Unhandled Action")
    }
}
