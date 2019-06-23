package icfp2019.analyzers

import icfp2019.core.Analyzer
import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.model.Node
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths
import org.jgrapht.graph.DefaultEdge

object ShortestPathUsingFlyodWarshall : Analyzer<ShortestPathAlgorithm<Node, DefaultEdge>> {
    override fun analyze(map: GameBoard): (state: GameState) -> ShortestPathAlgorithm<Node, DefaultEdge> {
        val completeGraph = GraphAnalyzer.analyze(map)
        return { graphState ->
            val graph = completeGraph(graphState)
            FloydWarshallShortestPaths(graph)
        }
    }
}