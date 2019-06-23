package icfp2019.core

import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.model.RobotId

interface Analyzer3<T> {
    fun analyze(initialState: GameState): (state: GameState) -> T
}

interface Analyzer<T> {
    fun analyze(map: GameBoard): (state: GameState) -> T
}

interface Analyzer2<T> {
    fun analyze(initialState: GameState): (robotId: RobotId, state: GameState) -> T
}
