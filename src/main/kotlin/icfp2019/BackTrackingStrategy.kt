package icfp2019

import java.util.*

// Move to an open space and push moves onto a stack, if no moves available then backtrack using the stack
class BackTrackingStrategy : Strategy {
    override fun getActions(gameState: GameState): Map<RobotId, List<Action>> {
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

                    currentState = tEngine.apply(
                        currentState, hashMapOf(
                            Pair<RobotId, Action>(RobotId(0), backTrackAction)
                        )
                    )
                    stack.push(backTrackAction)
                    moveList.add(backTrackAction)
                    availableMoves = availableMoves(currentState)
                }
            } else {
                // Else take the first available move
                val action = availableMoves.get(0)
                currentState = tEngine.apply(
                    currentState, hashMapOf(
                        Pair<RobotId, Action>(RobotId(0), action)
                    )
                )
                stack.push(action)
                moveList.add(action)
                availableMoves = availableMoves(currentState)
            }
        } while (stack.isNotEmpty() || availableMoves.isNotEmpty())

        val plans = hashMapOf<RobotId, List<Action>>(
            Pair<RobotId, List<Action>>(RobotId(0), moveList)
        )

        return plans
    }

    fun availableMoves(gameState: GameState): List<Action> {
        val moves = mutableListOf<Action>()

        val currentPosition = gameState.robotStateList[0].currentPosition

        fun canMoveTo(node: Node): Boolean {
            return node.isWrapped.not() && node.isObstacle.not()
        }

        if (currentPosition.y + 1 < gameState.gameBoard.height) {
            val up = gameState.gameBoard.get(currentPosition.up())
            if (canMoveTo(up)) {
                moves.add(Action.MoveUp)
            }
        }

        if (currentPosition.y - 1 > -1) {
            val down = gameState.gameBoard.get(currentPosition.down())
            if (canMoveTo(down)) {
                moves.add(Action.MoveDown)
            }
        }

        if (currentPosition.x - 1 > -1) {
            val left = gameState.gameBoard.get(currentPosition.left())
            if (canMoveTo(left)) {
                moves.add(Action.MoveLeft)
            }
        }

        if (currentPosition.x + 1 < gameState.gameBoard.width) {
            val right = gameState.gameBoard.get(currentPosition.right())
            if (canMoveTo(right)) {
                moves.add(Action.MoveRight)
            }
        }

        // do we care about the last move so we continue in that direction? would need it first in the list

        return moves
    }
}
