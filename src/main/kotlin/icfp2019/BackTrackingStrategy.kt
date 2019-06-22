package icfp2019

import java.lang.IllegalStateException
import java.util.*

// Move to an open space and push moves onto a stack, if no moves available then backtrack using the stack
class BackTrackingStrategy : Strategy {
    override fun getActions(gameState: GameState): Map<Int, List<Action>> {
        val stack = Stack<Action>()
        val moveList = mutableListOf<Action>()
        val tEngine = TransitionEngine()

        var currentState = gameState
        var availableMoves = availableMoves(gameState)
        do {
            if (availableMoves.isEmpty()) {
                // Backtrack if we can
                if (stack.isEmpty()) {
                    break
                } else {
                    val backTrackAction = stack.pop().invert() ?: throw IllegalStateException("Non-invertable action!")

                    currentState = tEngine.apply(currentState, hashMapOf(Pair<Int, Action>(0, backTrackAction)))
                    stack.push(backTrackAction)
                    moveList.add(backTrackAction)
                    availableMoves = availableMoves(gameState)
                }
            } else {
                // Else take the first available move
                val action = availableMoves.get(0)
                currentState = tEngine.apply(currentState, hashMapOf(Pair<Int, Action>(0, action)))
                stack.push(action)
                moveList.add(action)
                availableMoves = availableMoves(gameState)
            }
        } while (stack.isNotEmpty() || availableMoves.isNotEmpty())

        val plans = hashMapOf<Int, List<Action>>(
            Pair<Int, List<Action>>(0, moveList))

        return plans
    }

    fun availableMoves(gameState: GameState): List<Action> {
        // TODO: Implemenet available moves
        if (gameState.gameBoard.isNotEmpty()) {
            print("hi!")
        }

        return listOf<Action>()
    }
}