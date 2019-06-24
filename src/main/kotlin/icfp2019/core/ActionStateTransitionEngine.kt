package icfp2019.core

import icfp2019.model.*

fun applyAction(gameState: GameState, robotId: RobotId, action: Action): GameState {
    val currentPosition = gameState.robot(robotId).currentPosition
    return with(gameState) {
        when (action) {
            Action.DoNothing -> this
            Action.MoveUp -> move(robotId, Point::up)
            Action.MoveDown -> move(robotId, Point::down)
            Action.MoveLeft -> move(robotId, Point::left)
            Action.MoveRight -> move(robotId, Point::right)

            Action.TurnClockwise -> updateRobot(robotId) {
                copy(orientation = orientation.turnClockwise())
                copy(armRelativePoints = gameState.robot(robotId).turnClockwise())
            }

            Action.TurnCounterClockwise -> updateRobot(robotId) {
                copy(orientation = orientation.turnCounterClockwise())
                copy(armRelativePoints = gameState.robot(robotId).turnCounterClockwise())
            }

            Action.AttachFastWheels -> updateRobot(robotId) {
                copy(remainingFastWheelTime = this.remainingFastWheelTime + 50)
            }.useBoosterFromState(Booster.FastWheels)

            Action.StartDrill -> updateRobot(robotId) {
                copy(remainingDrillTime = this.remainingDrillTime + 30)
            }.useBoosterFromState(Booster.Drill)

            Action.PlantTeleportResetPoint -> updateBoard(
                currentPosition,
                get(currentPosition).copy(hasTeleporterPlanted = true)
            ).useBoosterFromState(Booster.Teleporter)

            Action.CloneRobot -> withNewRobot()

            is Action.TeleportBack -> updateRobot(robotId) {
                copy(currentPosition = action.targetResetPoint)
            }

            is Action.AttachManipulator -> updateRobot(robotId) {
                copy(armRelativePoints = armRelativePoints.plus(action.point))
            }.useBoosterFromState(Booster.ExtraArm)
        }
    }
}

private fun GameState.move(robotId: RobotId, mover: (Point) -> Point): GameState {
    val robotState = this.robot(robotId)

    val distance = if (robotState.hasActiveFastWheels()) 2 else 1

    val movedState = (0 until distance).fold(this) { state, _ ->
        val newPosition = mover(state.robot(robotId).currentPosition)
        state.updateRobot(robotId) { copy(currentPosition = newPosition) }
            .wrapAffectedCells(robotId)
            .addBoosterToState(newPosition)
    }
    return movedState.updateRobot(robotId) {
        copy(
            remainingFastWheelTime = if (robotState.hasActiveFastWheels()) robotState.remainingFastWheelTime - 1 else 0,
            remainingDrillTime = if (robotState.hasActiveDrill()) robotState.remainingDrillTime - 1 else 0
        )
    }
}

private fun GameState.updateRobot(robotId: RobotId, update: RobotState.() -> RobotState): GameState {
    val robotState = update.invoke(this.robot(robotId))
    return this.withRobotState(robotId, robotState)
}

private fun GameState.useBoosterFromState(booster: Booster): GameState {
    return this.copy(unusedBoosters = unusedBoosters.plus(booster to unusedBoosters.getOrDefault(booster, 1) - 1))
}

private fun GameState.addBoosterToState(point: Point): GameState {
    val node = this.nodeState(point)
    val booster = node.booster ?: return this
    // pickup
    return this.updateState(point, node.copy(booster = null)).let {
        it.copy(unusedBoosters = it.unusedBoosters.plus(booster to it.unusedBoosters.getOrDefault(booster, 0) + 1))
    }
}

private fun GameState.wrapAffectedCells(robotId: RobotId): GameState {
    val robot = this.robot(robotId)
    val robotPoint = robot.currentPosition
    val boardNode = this.get(robotPoint)

    val withUpdatedBoardState = if (boardNode.isObstacle)
        updateBoard(robotPoint, boardNode.copy(isObstacle = false))
    else this

    val updatedState = withUpdatedBoardState.updateState(robotPoint, this.nodeState(robotPoint).copy(isWrapped = true))

    return robot.armRelativePoints.fold(updatedState, { state, point ->
        val newPoint = robotPoint.applyRelativePoint(point)
        if (state.isInBoard(newPoint) && state.get(newPoint).isObstacle.not()) {
            val boardState = state.nodeState(newPoint)
            state.updateState(newPoint, boardState.copy(isWrapped = true))
        } else {
            state
        }
    })
}
