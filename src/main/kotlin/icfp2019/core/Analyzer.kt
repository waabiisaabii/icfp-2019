package icfp2019.core

import icfp2019.model.GameBoard
import icfp2019.model.GameState

interface Analyzer3<T> {
    fun analyze(initialState: GameState): (state: GameState) -> T
}

interface Analyzer<T> {
    fun analyze(map: GameBoard): (state: GameState) -> T
}
