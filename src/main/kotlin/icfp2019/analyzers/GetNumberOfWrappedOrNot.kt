package icfp2019.analyzers

import icfp2019.GameBoard
import icfp2019.GameState
import icfp2019.core.Analyzer

data class WrappedUnwrapped(val wrapped: Int, val unwrapped: Int)

class GetNumberOfWrappedOrNot : Analyzer<WrappedUnwrapped> {
    override fun analyze(map: GameBoard): (state: GameState) -> WrappedUnwrapped {
        return { _ ->
            WrappedUnwrapped(0, 0)
        }
    }
}
