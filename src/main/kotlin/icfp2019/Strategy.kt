package icfp2019

interface Strategy {
    // Given a state, return a list of actions, indexed by Robot id.
    fun getActions(gameState: GameState): Map<RobotId, List<Action>>
}