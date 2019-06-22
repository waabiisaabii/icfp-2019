package icfp2019.core

import icfp2019.GameBoard
import icfp2019.GameState

interface Strategy {
    fun compute(map: GameBoard): (state: GameState) -> Proposal
}
