package icfp2019.analyzers

import icfp2019.core.Analyzer
import icfp2019.model.Action
import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.strategies.DFSStrategy

object DFSAnalyzer : Analyzer<Iterable<Action>> {
    override fun analyze(map: GameBoard): (state: GameState) -> Iterable<Action> {
        return { graphState -> listOf(DFSStrategy.compute(map).invoke(graphState).nextMove) }
    }
}
