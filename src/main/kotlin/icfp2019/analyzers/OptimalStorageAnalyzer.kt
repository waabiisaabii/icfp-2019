package icfp2019.analyzers

import icfp2019.core.Analyzer
import icfp2019.model.Booster
import icfp2019.model.Cell
import icfp2019.model.GameBoard
import icfp2019.model.GameState

object OptimalStorageAnalyzer : Analyzer<Array<Short>> {
    override fun analyze(map: GameBoard): (state: GameState) -> Array<Short> {
        val cells = Array<Short>(map.height * map.width) { 0 }
        for (column in map.cells) {
            for (cell in column) {
                var flags: Short = 0
                if (cell.isObstacle) {
                    flags = Cell.setFlag(flags, Cell.OBSTACLE)
                } else {
                    flags = when (cell.booster) {
                        Booster.ExtraArm -> Cell.setFlag(flags, Cell.BOOST_EXT)
                        Booster.Drill -> Cell.setFlag(flags, Cell.BOOST_DRILL)
                        Booster.FastWheels -> Cell.setFlag(flags, Cell.BOOST_FAST)
                        Booster.Teleporter -> Cell.setFlag(flags, Cell.BOOST_TELEPORT)
                        Booster.CloneToken -> Cell.setFlag(flags, Cell.BOOST_CLONE)
                        else -> flags
                    }
                }
                cells[cell.point.x * map.height + cell.point.y] = flags
            }
        }
        return { cells }
    }
}
