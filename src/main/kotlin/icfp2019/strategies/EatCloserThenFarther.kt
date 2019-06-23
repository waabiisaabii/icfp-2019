package icfp2019.strategies

import icfp2019.analyzers.DistanceToWalls
import icfp2019.analyzers.GetNumberOfWrappedOrNot
import icfp2019.core.DistanceEstimate
import icfp2019.core.Proposal
import icfp2019.model.GameState
import icfp2019.core.Strategy2
import icfp2019.core.applyAction
import icfp2019.model.Action
import icfp2019.model.RobotId

class EatCloserThenFarther : Strategy2 {
    override fun compute(initialState: GameState): (state: GameState) -> Proposal {
        val distanceToWallsAnalyzer = DistanceToWalls().analyze(initialState)
        return { state ->
            // Assume one robot only

            // val currentDistance = distanceToWallsAnalyzer(0, state)
            val initialRobot = RobotId(0)
            val allMoves = listOf(
                    0 to (Action.MoveUp to state.robotState.getValue(initialRobot).currentPosition.up()),
                    1 to (Action.MoveRight to state.robotState.getValue(initialRobot).currentPosition.right()),
                    2 to (Action.MoveDown to state.robotState.getValue(initialRobot).currentPosition.down()),
                    3 to (Action.MoveLeft to state.robotState.getValue(initialRobot).currentPosition.left()))

            // [Index, GameState]
            val movesWithinGameboard = allMoves
                .filter { state.mapSize.pointInMap(it.second.second) }
                .map { it.first to applyAction(state, initialRobot, it.second.first) }
            // [Index, distance]
            val movesAvoidingObstacles = movesWithinGameboard
                .map { it.first to distanceToWallsAnalyzer(initialRobot, it.second) }
                .filter { it.second != DistanceToWalls.obstacleIdentifier }

            // Deal with wrapped vs unwrapped. If all wrapped, go for the largest.
            // Else, go for the smallest.
            val allWrapped = movesAvoidingObstacles
                .map { it.first }
                .filter { !state.get(allMoves[it].second.second).isWrapped }
                .isEmpty()

            val maxDistance = Int.MAX_VALUE
            val wrappedUnwrapped = GetNumberOfWrappedOrNot.analyze(initialState)(state)
            val distance = if (allWrapped) {
                maxDistance
            } else {
                wrappedUnwrapped.unwrapped
            }
            val result = if (allWrapped) {
                movesAvoidingObstacles
                    .maxBy { it.second }
            } else {
                movesAvoidingObstacles
                    .filter { !state.get(allMoves[it.first].second.second).isWrapped }
                    .minBy { it.second }
            }

            if (result != null) {
                Proposal(DistanceEstimate(distance), allMoves[result.first].second.first)
            } else {
                Proposal(DistanceEstimate(maxDistance), Action.DoNothing)
            }
        }
    }
}
