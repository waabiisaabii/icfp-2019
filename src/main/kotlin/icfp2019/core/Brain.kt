package icfp2019.core

import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.model.RobotId

object Brain : MoveSelector {
    override fun selectFrom(strategies: Iterable<Strategy>): (map: GameBoard) -> (robotId: RobotId, state: GameState) -> Proposal {
        return { map ->
            { _, state ->
                strategies.map {
                    it.compute(map)(state)
                }.minBy { it.estimatedDistance.distance } ?: throw IllegalStateException()
            }
        }
    }
}
