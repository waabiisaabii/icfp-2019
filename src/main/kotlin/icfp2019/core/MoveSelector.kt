package icfp2019.core

import icfp2019.GameBoard
import icfp2019.GameState

interface MoveSelector {
    fun selectFrom(strategies: Iterable<Strategy>): (map: GameBoard) -> (state: GameState) -> Proposal
}
