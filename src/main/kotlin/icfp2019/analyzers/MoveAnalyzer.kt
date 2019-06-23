package icfp2019.analyzers

import icfp2019.model.*
import icfp2019.core.Analyzer

object MoveAnalyzer : Analyzer<(RobotId, Action) -> Boolean> {
    override fun analyze(map: GameBoard): (state: GameState) -> (RobotId, Action) -> Boolean {
        return { gameState ->
            { robotId, action ->
                var possible = false

                fun getRobotState(robotId: RobotId): RobotState? {
                    for (state: RobotState in gameState.robotStateList) {
                        if (state.robotId == robotId) {
                            return state
                        }
                    }
                    return null
                }

                val robotState = getRobotState(robotId)
                if (robotState != null &&
                    map.isInBoard(robotState.currentPosition)) {
                    val cell = map.get(robotState.currentPosition)

                    fun canMoveTo(point: Point): Boolean {
                        return map.isInBoard(point) && !cell.isObstacle
                    }

                    fun canTeleportTo(point: Point): Boolean {
                        for (location: Point in gameState.teleportDestination) {
                            if (location == point) {
                                return true
                            }
                        }
                        return false
                    }

                    fun hasBooster(booster: Booster): Boolean {
                        for (possibleBooster: Booster in gameState.unusedBoosters) {
                            if (booster == possibleBooster) {
                                return true
                            }
                        }
                        return false
                    }

                    possible = when (action) {
                        Action.MoveUp -> canMoveTo(robotState.currentPosition.up())
                        Action.MoveDown -> canMoveTo(robotState.currentPosition.down())
                        Action.MoveLeft -> canMoveTo(robotState.currentPosition.left())
                        Action.MoveRight -> canMoveTo(robotState.currentPosition.right())
                        Action.DoNothing -> true
                        Action.TurnClockwise -> true
                        Action.TurnCounterClockwise -> true
                        is Action.AttachManipulator -> hasBooster(Booster.ExtraArm)
                        Action.AttachFastWheels -> hasBooster(Booster.FastWheels)
                        Action.StartDrill -> hasBooster(Booster.Drill)
                        Action.PlantTeleportResetPoint -> hasBooster(Booster.Teleporter)
                        is Action.TeleportBack -> canTeleportTo(action.targetResetPoint)
                        Action.CloneRobot -> hasBooster(Booster.CloneToken) &&
                                map.get(robotState.currentPosition).hasBooster(Booster.CloningLocation)
                    }
                }
                possible
            }
        }
    }
}
