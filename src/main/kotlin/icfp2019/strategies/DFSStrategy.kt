package icfp2019.strategies

import icfp2019.analyzers.GraphAnalyzer
import icfp2019.analyzers.ShortestPathUsingFlyodWarshall
import icfp2019.core.DistanceEstimate
import icfp2019.core.Proposal
import icfp2019.core.Strategy
import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.model.Node
import org.jgrapht.Graph
import org.jgrapht.GraphPath
import org.jgrapht.graph.AsSubgraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.traverse.DepthFirstIterator
import org.jgrapht.traverse.GraphIterator

// Move to an open space and push moves onto a stack, if no moves available then backtrack using the stack
object DFSStrategy : Strategy {
    override fun compute(map: GameBoard): (state: GameState) -> Proposal {
        return { gameState ->
            val graph: Graph<Node, DefaultEdge> = GraphAnalyzer.analyze(map).invoke(gameState)

            val currentPoint = gameState.robotState.values.first().currentPosition
            val currentNode = gameState.get(currentPoint)

            val unwrappedGraph =
                AsSubgraph(graph, graph.vertexSet().filter { it.isWrapped.not() }.plus(currentNode).toSet())

            val it: GraphIterator<Node, DefaultEdge> = DepthFirstIterator(unwrappedGraph)

            val neighbors = currentNode.point.neighbors().map { gameState.get(it) }
            if (neighbors.any {
                    it.isWrapped.not() && it.isObstacle.not()
                }) {
                val neighbor = it.next().point
                Proposal(
                    DistanceEstimate(0),
                    currentPoint.actionToGetToNeighbor(neighbor)
                )
            } else {
                val analyze = ShortestPathUsingFlyodWarshall.analyze(map)
                val shortestPathAlgorithm = analyze(gameState)

                val pathToClosestNode: GraphPath<Node, DefaultEdge> = unwrappedGraph.vertexSet()
                    .filter { it.point != currentNode.point }
                    .filter { it.isWrapped.not() }
                    .map { shortestPathAlgorithm.getPath(currentNode, it) }
                    .minBy { it.length }!!

                // pathToClosestNode.vertexList[0] is `currentNode`
                val nextNode = pathToClosestNode.vertexList[1]
                Proposal(
                    DistanceEstimate(0),
                    currentPoint.actionToGetToNeighbor(nextNode.point)
                )
            }
        }
    }
}
