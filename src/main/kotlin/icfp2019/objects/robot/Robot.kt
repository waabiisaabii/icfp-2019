package icfp2019.objects.robot

import icfp2019.Point

data class RobotState(
    val robotId: RobotId,
    val currentPosition: Point,
    val orientation: Orientation = Orientation.Up,
    val remainingFastWheelTime: Int? = null,
    val remainingDrillTime: Int? = null
)

data class RobotId(val id: Int)

enum class Orientation {
    Up, Down, Left, Right
}