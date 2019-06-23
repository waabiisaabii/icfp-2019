package icfp2019.strategies

import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.core.Strategy
import icfp2019.model.Action

class EatCloserThenFarther : Strategy {
    override fun compute(map: GameBoard): (state: GameState) -> Iterable<Action> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
