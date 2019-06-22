package icfp2019.analyzers

import icfp2019.GameBoard
import icfp2019.GameState
import icfp2019.Node
import icfp2019.core.Analyzer
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

object GraphAnalyzer : Analyzer<Graph<Node, DefaultEdge>> {
    override fun analyze(map: GameBoard): (state: GameState) -> Graph<Node, DefaultEdge> {

        val simpleGraph = SimpleGraph<Node, DefaultEdge>(DefaultEdge::class.java)
        val allCells = map.cells.flatten().filter { !it.isObstacle }

        allCells.forEach {
            simpleGraph.addVertex(it)
        }

        allCells.forEach { n1 ->
            allCells.forEach { n2 ->
                if (n1.point.isNeighbor(n2.point)) {
                    simpleGraph.addEdge(n1, n2)
                }
            }
        }

        return { simpleGraph }
    }
}
