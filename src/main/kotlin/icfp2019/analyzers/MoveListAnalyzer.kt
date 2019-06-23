package icfp2019.analyzers

import icfp2019.core.Analyzer
import icfp2019.model.*

object MoveListAnalyzer : Analyzer<(RobotId) -> List<Action>> {
    override fun analyze(map: GameBoard): (state: GameState) -> (RobotId) -> List<Action> {
        return { gameState ->
            { robotId ->
                val moves = mutableListOf<Action>()
                moves.add(Action.DoNothing)

                fun getRobotState(robotId: RobotId): RobotState? {
                    for (state: RobotState in gameState.robotStateList) {
                        if (state.robotId == robotId) {
                            return state
                        }
                    }
                    return null
                }

                val robotState = getRobotState(robotId)

                // Asking about a bad location... No moves
                if (robotState != null &&
                    map.isInBoard(robotState.currentPosition)) {
                    val cell = map.get(robotState.currentPosition)

                    // Things independent of point or location
                    moves.add(Action.DoNothing)
                    moves.add(Action.TurnClockwise)
                    moves.add(Action.TurnCounterClockwise)
                    moves.add(Action.PlantTeleportResetPoint)
                    for (location: Point in gameState.teleportDestination) {
                        moves.add(Action.TeleportBack(location))
                    }

                    fun canMoveTo(point: Point): Boolean {
                        return map.isInBoard(point) && !cell.isObstacle
                    }

                    // Check directions
                    if (canMoveTo(robotState.currentPosition.up())) {
                        moves.add(Action.MoveUp)
                    }

                    if (canMoveTo(robotState.currentPosition.down())) {
                        moves.add(Action.MoveDown)
                    }

                    if (canMoveTo(robotState.currentPosition.right())) {
                        moves.add(Action.MoveRight)
                    }

                    if (canMoveTo(robotState.currentPosition.left())) {
                        moves.add(Action.MoveLeft)
                    }

                    // Are we on a clone point?
                    if (cell.hasBooster(Booster.CloningLocation)) {
                        moves.add(Action.CloneRobot)
                    }
                }

                moves
            }
        }
    }
}
