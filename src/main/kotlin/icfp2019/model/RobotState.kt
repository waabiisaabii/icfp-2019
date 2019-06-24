package icfp2019.model

import java.lang.Math.*
import kotlin.math.roundToInt

data class RobotState(
    val robotId: RobotId,
    val currentPosition: Point,
    val orientation: Orientation = Orientation.Right,
    val remainingFastWheelTime: Int = 0,
    val remainingDrillTime: Int = 0,
    val armRelativePoints: List<Point> = listOf(Point(1, 0), Point(1, 1), Point(1, -1))
) {
    fun hasActiveDrill(): Boolean {
        return remainingDrillTime > 0
    }

    fun hasActiveFastWheels(): Boolean {
        return remainingFastWheelTime > 0
    }

    fun turnArmWithDegree(rotate: Double): List<Point> {
        return listOf(Point(1, 0), Point(1, 1), Point(1, -1))
            .map { rotatePoint(it, rotate) }
    }

    fun rotatePoint(point: Point, theta: Double): Point {
        val x = point.x
        val y = point.y
        return Point(
            ((cos(theta) * x - sin(theta) * y).roundToInt()),
            ((cos(theta) * y + sin(theta) * x).roundToInt())
        )
    }
}
