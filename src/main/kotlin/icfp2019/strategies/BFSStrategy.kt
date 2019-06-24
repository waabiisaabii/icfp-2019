package icfp2019.strategies

import icfp2019.analyzers.GraphAnalyzer
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
        val graph = GraphAnalyzer.analyze(initialState).invoke(RobotId(0), initialState)
        return { robotId, gameState ->
            val currentPoint = gameState.robotState.values.first().currentPosition
            val currentNode = graph.vertexSet().filter { currentPoint == it.point }[0]

            val unwrappedGraph =
                AsSubgraph(graph, graph.vertexSet().filter { gameState.get(it.point).isWrapped.not() }.plus(currentNode).toSet())

            val bfsIterator: GraphIterator<Node, DefaultEdge> = BreadthFirstIterator(unwrappedGraph, currentNode)

            //look ahead


            val orientations = listOf(Orientation.Right, Orientation.Left, Orientation.Up, Orientation.Down)
                .map {
                    val tmpIterator: GraphIterator<Node, DefaultEdge> =
                        BreadthFirstIterator(unwrappedGraph, currentNode)
                    // skip current node
                    if (!tmpIterator.hasNext())
                        Pair(0, it)

                    val currentRobotState = gameState.robot(robotId)

                    val tmpRobotState = RobotState(robotId,
                        currentPoint,
                        currentRobotState.orientation,
                        currentRobotState.remainingFastWheelTime,
                        currentRobotState.remainingDrillTime,
                        currentRobotState.turnArm(it)
                    )

                    val curNode = tmpIterator.next()
                    val numToBeWrapped = curNode.point.neighbors()
                        .filter { point ->
                            gameState.isInBoard(point)
                                && gameState.get(point).isWrapped.not()
                                && gameState.get(point).isObstacle.not()
                                && tmpRobotState.armRelativePoints.contains(point)}
                        .count()

                    if (!tmpIterator.hasNext())
                        Pair(numToBeWrapped, it)

                    val nextNode = tmpIterator.next()
                    val numToBeWrapped2 = nextNode.point.neighbors()
                        .filter { point ->
                            gameState.isInBoard(point)
                                && gameState.get(point).isWrapped.not()
                                && gameState.get(point).isObstacle.not()
                                && tmpRobotState.armRelativePoints.contains(point)}
                        .count()
                    Pair(numToBeWrapped + numToBeWrapped2, it)
                }.maxBy { it.first }
            println(orientations)

            val neighbors = currentNode.point.neighbors()
                .filter { gameState.isInBoard(it) }
                .map { gameState.get(it) }
            if (neighbors.any {
                    it.isWrapped.not() && it.isObstacle.not()
                }) {
                bfsIterator.next() // move past currentNode
                val neighbor = bfsIterator.next().point
                currentPoint.actionToGetToNeighbor(neighbor)
            } else {
                val analyze = ShortestPathUsingDijkstra.analyze(gameState)
                val shortestPathAlgorithm = analyze(robotId, gameState)

                val pathToClosestNode: GraphPath<Node, DefaultEdge> = unwrappedGraph.vertexSet()
                    .filter { it.point != currentNode.point }
                    .filter { it.isWrapped.not() }
                    .map { shortestPathAlgorithm.getPath(gameState.get(currentPoint), it) }
                    .minBy { it.length }!!

                // pathToClosestNode.vertexList[0] is `currentNode`
                val nextNode = pathToClosestNode.vertexList[1]
                currentPoint.actionToGetToNeighbor(nextNode.point)
            }
        }
    }
}
