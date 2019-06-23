package icfp2019.core

import icfp2019.model.GameBoard
import icfp2019.model.GameState

interface Strategy {
    fun compute(map: GameBoard): (state: GameState) -> Proposal
}
