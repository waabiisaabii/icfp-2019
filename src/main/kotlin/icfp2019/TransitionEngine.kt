package icfp2019

class TransitionEngine {
    fun apply(gameState: GameState, actions: Map<Int, Action>): GameState {
        // TODO: implement later

        actions.forEach { println("yay!" + it.key.toString()) }

        return GameState(gameState.gameBoard, listOf(), listOf(), listOf())
    }
}