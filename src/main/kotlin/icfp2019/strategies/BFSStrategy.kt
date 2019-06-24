package icfp2019.strategies

import icfp2019.analyzers.BoardCellsGraphAnalyzer
import icfp2019.analyzers.ShortestPathUsingDijkstra
import icfp2019.core.Strategy
import icfp2019.model.*
import org.jgrapht.GraphPath
import org.jgrapht.graph.AsSubgraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.traverse.BreadthFirstIterator
import org.jgrapht.traverse.GraphIterator

object BFSStrategy : Strategy {
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

            val neighbors = currentNode.point.neighbors()
                .filter { gameState.isInBoard(it) }
                .map { gameState.get(it) }
            if (neighbors.any {
                    it.point in unWrappedPoints && it.isObstacle.not()
                }) {
                bfsIterator.next() // move past currentNode
                val neighbor = bfsIterator.next().point
                currentPoint.actionToGetToNeighbor(neighbor)
            } else {
                val analyze = ShortestPathUsingDijkstra.analyze(gameState)
                val shortestPathAlgorithm = analyze(robotId, gameState)

                val pathToClosestNode: GraphPath<BoardCell, DefaultEdge> = unwrappedGraph.vertexSet()
                    .filter { it.point != currentNode.point }
                    .filter { it.point in unWrappedPoints }
                    .map { shortestPathAlgorithm.getPath(gameState.get(currentPoint), it) }
                    .minBy { it.length }!!

                // pathToClosestNode.vertexList[0] is `currentNode`
                val nextNode = pathToClosestNode.vertexList[1]
                currentPoint.actionToGetToNeighbor(nextNode.point)
            }
        }
    }
}
