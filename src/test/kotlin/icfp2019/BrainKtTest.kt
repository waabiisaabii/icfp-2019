package icfp2019

import icfp2019.core.DistanceEstimate
import icfp2019.core.Proposal
import icfp2019.core.Strategy
import icfp2019.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import java.lang.IllegalStateException

internal class BrainKtTest {
    object RightStrategy : Strategy {
        override fun compute(initialState: GameState): (robotId: RobotId, state: GameState) -> Proposal {
            return { _, gameState ->
                val position = gameState
                    .robotState
                    .getValue(RobotId.first)
                    .currentPosition

                when (position) {
                    Point(0, 2) -> Proposal(DistanceEstimate(5), Action.MoveRight)
                    Point(0, 1) -> Proposal(DistanceEstimate(4), Action.MoveRight)
                    Point(1, 1) -> Proposal(DistanceEstimate(1), Action.MoveRight)
                    Point(2, 1) -> Proposal(DistanceEstimate(Int.MAX_VALUE), Action.MoveRight)
                    Point(2, 0) -> Proposal(DistanceEstimate(Int.MAX_VALUE), Action.MoveRight)
                    else -> throw IllegalStateException("$position")
                }
            }
        }
    }

    object DownStrategy : Strategy {
        override fun compute(initialState: GameState): (robotId: RobotId, state: GameState) -> Proposal {
            return { _, gameState ->
                val position = gameState
                    .robotState
                    .getValue(RobotId.first)
                    .currentPosition

                when (position) {
                    Point(0, 2) -> Proposal(DistanceEstimate(2), Action.MoveDown)
                    Point(0, 1) -> Proposal(DistanceEstimate(4), Action.MoveDown)
                    Point(1, 1) -> Proposal(DistanceEstimate(4), Action.MoveDown)
                    Point(2, 1) -> Proposal(DistanceEstimate(4), Action.MoveDown)
                    Point(2, 0) -> Proposal(DistanceEstimate(4), Action.MoveDown)
                    else -> throw IllegalStateException("$position")
                }
            }
        }
    }

    @Test
    fun brainStep() {
        val problem = parseTestMap(init)
        val solution = parseTestMap(fini)
        printBoard(problem)
        var state = GameState.gameStateOf(problem)
        for (i in 0..3) {
            val (result, actions) = brainStep(
                state,
                listOf(RightStrategy, DownStrategy)
            )

            state = result

            println(actions)
            val problem0 = problem.copy(map = result.cells)
            printBoard(problem0)
        }

        Assertions.assertEquals(solution.map, state.cells)
    }

    private val init =
        """
        @..
        ...
        ...
        """

    private val fini =
        """
        @..
        www
        ..w
        """
}
