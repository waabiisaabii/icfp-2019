package icfp2019.model

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

    fun turnArm(orientation: Orientation): List<Point> =
        when (orientation) {
            Orientation.Down -> turnArmDown()
            Orientation.Up -> turnArmUp()
            Orientation.Left -> turnArmLeft()
            Orientation.Right -> turnArmRight()
        }

    private fun turnArmRight(): List<Point> {
        return listOf(Point(1, 0), Point(1, 1), Point(1, -1))
    }

    private fun turnArmLeft(): List<Point> {
        return listOf(Point(-1, 0), Point(-1, 1), Point(-1, -1))
    }

    private fun turnArmUp(): List<Point> {
        return listOf(Point(1, 1), Point(0, 1), Point(-1, 1))
    }

    private fun turnArmDown(): List<Point> {
        return listOf(Point(1, -1), Point(0, -1), Point(-1, -1))
    }
}
