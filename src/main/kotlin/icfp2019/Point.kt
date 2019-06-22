package icfp2019

data class Point(val x: Int, val y: Int) {
    fun isNeighbor(otherPoint: Point): Boolean = when (otherPoint) {
        left() -> true
        right() -> true
        up() -> true
        down() -> true
        else -> false
    }

    fun up(): Point {
        return copy(y = y + 1)
    }

    fun down(): Point {
        return copy(y = y - 1)
    }

    fun left(): Point {
        return copy(x = x - 1)
    }

    fun right(): Point {
        return copy(x = x + 1)
    }
}
