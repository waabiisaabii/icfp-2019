package icfp2019.strategies

import icfp2019.analyzers.BoardCellsGraphAnalyzer
import icfp2019.analyzers.ShortestPathUsingDijkstra
import icfp2019.core.Strategy
import icfp2019.core.applyAction
import icfp2019.model.*
import org.jgrapht.GraphPath
import org.jgrapht.graph.AsSubgraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.traverse.BreadthFirstIterator
import org.jgrapht.traverse.GraphIterator

object BFSStrategy : Strategy {
    fun getNumWrappedOnArm(
        gameState: GameState,
        robotId: RobotId
    ): Int {
        val armOffsets = gameState.robot(robotId).armRelativePoints
        val currentPoint = gameState.robot(robotId).currentPosition
        val numToBeWrapped = armOffsets.filter {
            val armOnMap = it.applyRelativePoint(currentPoint)
            gameState.isInBoard(armOnMap)
                    && gameState.get(armOnMap).isObstacle.not()
                    && gameState.nodeState(armOnMap).isWrapped.not()
        }.count()
        return numToBeWrapped
    }

    override fun compute(initialState: GameState): (robotId: RobotId, state: GameState) -> Action {
        val graphBuilder = BoardCellsGraphAnalyzer.analyze(initialState)
        return { robotId, gameState ->
            val unWrappedPoints = gameState.boardState().allStates().filter { !it.isWrapped }.map { it.point }
            val graph = graphBuilder.invoke(robotId, gameState)
            val currentPoint = gameState.robot(robotId).currentPosition
            val currentNode = graph.vertexSet().filter { currentPoint == it.point }[0]

            val unwrappedGraph =
                AsSubgraph(
                    graph,
                    graph.vertexSet().filter { it.point in unWrappedPoints }.plus(currentNode).toSet()
                )

            val bfsIterator: GraphIterator<BoardCell, DefaultEdge> = BreadthFirstIterator(unwrappedGraph, currentNode)

            // look ahead

            val currentOrientation = gameState.robot(robotId).orientation
            val action = listOf(
                Pair(currentOrientation.turnClockwise(), "Clockwise"),
                Pair(currentOrientation.turnCounterClockwise(), "CounterClockwise"),
                Pair(currentOrientation, "DoNothing")
            )
                .map {
                    val newState = when (it.second) {
                        "Clockwise" -> applyAction(gameState, robotId, Action.TurnClockwise)
                        "CountClockwise" -> applyAction(gameState, robotId, Action.TurnCounterClockwise)
                        else -> applyAction(gameState, robotId, Action.DoNothing)
                    }

                    val toBeWrapped = getNumWrappedOnArm(newState, robotId)
                    val toBeWrapped2 = getNumWrappedOnArm(newState, robotId)
                    val action = when (it.second) {
                        "Clockwise" -> Action.TurnClockwise
                        "CounterClockwise" -> Action.TurnCounterClockwise
                        else -> Action.DoNothing
                    }
                    Pair(toBeWrapped + toBeWrapped2, action)
                }
                .maxBy { it.first }?.second

            if (action != Action.DoNothing) {
                println(action)
                action!!
            } else {
                var newState = applyAction(gameState, robotId, action)
                val neighbors = currentNode.point.neighbors()
                    .filter { newState.isInBoard(it) }
                    .map { newState.get(it) }
                if (neighbors.any {
                        it.point in unWrappedPoints && it.isObstacle.not()
                    }) {
                    bfsIterator.next() // move past currentNode
                    val neighbor = bfsIterator.next().point
                    currentPoint.actionToGetToNeighbor(neighbor)
                } else {
                    val analyze = ShortestPathUsingDijkstra.analyze(newState)
                    val shortestPathAlgorithm = analyze(robotId, newState)

                    val pathToClosestNode: GraphPath<BoardCell, DefaultEdge> = unwrappedGraph.vertexSet()
                        .filter { it.point != currentNode.point }
                        .filter { it.point in unWrappedPoints }
                        .map { shortestPathAlgorithm.getPath(newState.get(currentPoint), it) }
                        .minBy { it.length }!!

                    // pathToClosestNode.vertexList[0] is `currentNode`
                    val nextNode = pathToClosestNode.vertexList[1]
                    currentPoint.actionToGetToNeighbor(nextNode.point)
                }
            }
        }
    }
}
