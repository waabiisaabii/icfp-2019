package icfp2019.core

import icfp2019.GameBoard
import icfp2019.GameState

interface Analyzer<T> {
    fun analyze(map: GameBoard): (state: GameState) -> T
}
