package icfp2019.analyzers

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import icfp2019.Booster
import icfp2019.Cell
import icfp2019.GameBoard
import icfp2019.GameState
import icfp2019.core.Analyzer

object OptimalStorageAnalyzer : Analyzer<Array<Short>> {
    private val cache = CacheBuilder
        .newBuilder()
        .build(object : CacheLoader<GameBoard, Array<Short>>() {
            override fun load(map: GameBoard): Array<Short> {
                val cells = Array<Short>(map.height * map.width) { 0 }
                for (column in map.cells) {
                    for (cell in column) {
                        val offset = cell.point.x * map.height + cell.point.y
                        cells[offset] = when {
                            cell.isObstacle -> Cell.OBSTACLE
                            cell.booster == Booster.ExtraArm -> Cell.BOOST_EXT
                            cell.booster == Booster.Drill -> Cell.BOOST_DRILL
                            cell.booster == Booster.FastWheels -> Cell.BOOST_FAST
                            cell.booster == Booster.Teleporter -> Cell.BOOST_TELEPORT
                            cell.booster == Booster.CloneToken -> Cell.BOOST_CLONE
                            else -> Cell.ZERO
                        }
                    }
                }

                return cells
            }
        })

    override fun analyze(map: GameBoard): (state: GameState) -> Array<Short> {
        return { cache.getUnchecked(map) }
    }
}
