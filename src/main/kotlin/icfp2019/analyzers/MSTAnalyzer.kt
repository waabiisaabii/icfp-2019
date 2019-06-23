package icfp2019.analyzers

import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.core.Analyzer
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree
import org.jgrapht.graph.DefaultEdge

object MSTAnalyzer : Analyzer<SpanningTreeAlgorithm.SpanningTree<DefaultEdge>> {
    override fun analyze(map: GameBoard): (state: GameState) -> SpanningTreeAlgorithm.SpanningTree<DefaultEdge> {
        val completeGraph = GraphAnalyzer.analyze(map)
        return { graphState ->
            val graph = completeGraph(graphState)
            KruskalMinimumSpanningTree(graph).spanningTree
        }
    }
}