package icfp2019

import com.google.common.base.CharMatcher
import com.google.common.collect.Range
import com.google.common.collect.TreeRangeSet

val matcher = CharMatcher.anyOf("()")

fun parsePoint(mapEdges: String): Point {
  return parseEdges(mapEdges)[0]
}

fun parseEdges(mapEdges: String): List<Point> {
  return mapEdges.split(',')
      .map { matcher.trimFrom(it) }
      .windowed(2, step = 2)
      .map { Point(Integer.parseInt(it[0]), Integer.parseInt(it[1])) }
}

fun parseDesc(problem: ProblemDescription): Problem {

  val (mapEdges, startPosition, obstacles, boosters) = problem.line.split('#')
  val startPoint = parsePoint(startPosition)
  val verticies = parseEdges(mapEdges)
  val obstacleEdges = parseEdges(obstacles)
  val parsedBosters = parseBoosters(boosters)

  val maxY = verticies.maxBy { it.y }?.y ?: throw RuntimeException()
  val maxX = verticies.maxBy { it.x }?.x ?: throw RuntimeException()
  println("$maxX,$maxY")

  val grid = (0.until(maxX)).map { x ->
    (0.until(maxY)).map { y ->
      Node(Point(x, y), isObstacle = true, booster = null)
    }.toTypedArray()
  }.toTypedArray()

  verticies.forEach {
    val x  = it.x;
    val y = it.y
    println("$x,$y")
    if (x < maxX && y < maxY) {
      var xRow = grid[x]
      xRow[y] = xRow[y].copy(isObstacle = false)
    }
  }

  val xGroups = verticies.groupBy { it.x }.mapValues {
    val set: TreeRangeSet<Int> = TreeRangeSet.create()
    val sortedBy = it.value.map { it.y }.sortedBy { it }
    sortedBy.windowed(2, step = 2).forEach {
      val (y1, y2) = it
      set.add(Range.closed(y1, y2))
    }

    set
  }

  var inObstacle = true
  (0 until maxX).forEach { x ->
    (0 until maxY).forEach { y ->
      if (xGroups[x]?.contains(y) == true) {
        inObstacle = !inObstacle
      }
      grid[x][y] = grid[x][y].copy(isObstacle = inObstacle)
    }
  }

  // Read lines
  /*
  1. Read lines
  2. Parse map
  Grammar:
    x,y: Nat
              point ::= (x,y)
                map ::= repSep(point,”,”)
        BoosterCode ::= B|F|L|X
    boosterLocation ::= BoosterCode point
          obstacles ::= repSep(map,”; ”)
           boosters ::= repSep(boosterLocation,”; ”)
               task ::= map # point # obstacles # boosters
   */
  return Problem(problem.problemId, Size(maxX, maxY), startPoint, grid)
}

fun parseBoosters(boosters: String): List<Pair<Boosters, Point>> {
  return boosters.split(";")
          .map { Boosters.valueOf(it[0].toString()).to(parsePoint(it.substring(1))) }
}


