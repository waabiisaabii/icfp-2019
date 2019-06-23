package icfp2019

import icfp2019.analyzers.GetNumberOfWrappedOrNot
import icfp2019.core.Strategy
import icfp2019.core.applyAction
import icfp2019.model.*

fun brainStep(
    gameBoard: GameBoard,
    initialGameState: GameState,
    strategies: Iterable<Strategy>
): Pair<GameState, Map<RobotId, Action>> {

    // one time step consists of advancing each worker wrapper by one move
    // this will determine the order in which to advance robots by running
    // all robot state through all strategies, picking the the winning
    // robot/state pair and resuming until the stack of robots is empty

    // the list of robots can change over time steps, get a fresh copy each iteration
    var gameState = initialGameState
    val actions = mutableMapOf<RobotId, Action>()
    val workingSet = gameState.robotState.keys.toMutableSet()
    while (workingSet.isNotEmpty()) {
        // pick the minimum across all robot/strategy pairs
        val winner = workingSet
            .flatMap { robotId ->
                // TODO: advance for robotId
                strategies.map { robotId to it.compute(gameBoard)(gameState) }
            }.minBy { it.second }!!

        // we have a winner, remove it from the working set for this time step
        workingSet.remove(winner.first)
        // record the winning action and update the running state
        actions[winner.first] = winner.second.nextMove
        gameState = applyAction(gameState, winner.first, winner.second.nextMove)
    }

    return gameState to actions
}

fun brain(problem: Problem, strategies: Iterable<Strategy>): String {
    val gameBoard = GameBoard(problem)
    var gameState = GameState.gameStateOf(problem)
    val actions = mutableMapOf<RobotId, List<Action>>()
    val getNumberOfWrapped = GetNumberOfWrappedOrNot.analyze(gameState)
    fun isNotFinished(gameState: GameState): Boolean {
        return getNumberOfWrapped(gameState).unwrapped > 0
    }

    while (isNotFinished(gameState)) {
        val (newState, newActions) = brainStep(gameBoard, gameState, strategies)

        gameState = newState
        newActions.forEach { (robotId, action) ->
            actions.merge(robotId, listOf(action)) { left, right -> left.plus(right) }
        }
    }

    return actions.encodeActions()
}
