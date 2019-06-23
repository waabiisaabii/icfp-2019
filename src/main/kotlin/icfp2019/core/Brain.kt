package icfp2019.core

import icfp2019.model.GameState
import icfp2019.model.RobotId

object Brain : MoveSelector {
    override fun selectFrom(strategies: Iterable<Strategy>): (initialState: GameState) -> (robotId: RobotId, state: GameState) -> Proposal {
        return { map ->
            { id, state ->
                strategies.map {
                    it.compute(map)(id, state)
                }.minBy { it.estimatedDistance.distance } ?: throw IllegalStateException()
            }
        }
    }
}
