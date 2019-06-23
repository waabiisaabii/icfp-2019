package icfp2019.analyzers

import icfp2019.model.GameState
import icfp2019.core.Analyzer3

data class WrappedUnwrapped(val wrapped: Int, val unwrapped: Int)

object GetNumberOfWrappedOrNot : Analyzer3<WrappedUnwrapped> {
    override fun analyze(initialState: GameState): (state: GameState) -> WrappedUnwrapped {
        return { state ->
            state.cells
                .flatten()
                .map {
                    if (it.isWrapped) {
                        WrappedUnwrapped(1, 0)
                    } else {
                        WrappedUnwrapped(0, 1)
                    }
                }
                .reduce { a, b -> WrappedUnwrapped(a.wrapped + b.wrapped, a.unwrapped + b.unwrapped) }
        }
    }
}
