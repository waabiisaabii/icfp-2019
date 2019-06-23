package icfp2019

import icfp2019.model.Action
import icfp2019.model.GameState
import icfp2019.model.RobotId

interface Strategy {
    // Given a state, return a list of actions, indexed by Robot id.
    fun getActions(gameState: GameState): Map<RobotId, List<Action>>
}