package icfp2019.core

import icfp2019.model.GameBoard
import icfp2019.model.GameState

interface Analyzer<T> {
    fun analyze(map: GameBoard): (state: GameState) -> T
}
