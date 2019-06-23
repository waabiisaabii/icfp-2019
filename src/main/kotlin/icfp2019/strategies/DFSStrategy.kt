package icfp2019.strategies

import icfp2019.analyzers.GraphAnalyzer
import icfp2019.analyzers.MoveListAnalyzer
import icfp2019.core.*
import icfp2019.model.*
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.traverse.DepthFirstIterator
import org.jgrapht.traverse.GraphIterator

// Move to an open space and push moves onto a stack, if no moves available then backtrack using the stack
object DFSStrategy : Strategy {
    override fun compute(map: GameBoard): (state: GameState) -> Iterable<Action> {
        return { gameState ->
            val undirectedGraph: Graph<Node, DefaultEdge> = GraphAnalyzer.analyze(map).invoke(gameState)
            val it: GraphIterator<Node, DefaultEdge> = DepthFirstIterator<Node, DefaultEdge>(undirectedGraph)
            val visitedMap = mutableMapOf<Node, Boolean>()

            val traversalList: MutableList<Action> = mutableListOf()
            while (it.hasNext()) {
                val currentNode: Node = it.next()
                if (!visitedMap.containsKey(currentNode)) {
                    // Consume the node if we haven't seen the node before
                    val moves: List<Action> =
                        when (!currentNode.isWrapped) {
                            true -> MoveListAnalyzer.analyze(map)
                                .invoke(gameState)
                                .invoke(gameState.robotStateList.get(0).robotId)
                            false -> listOf()
                        }
                    traversalList.add(pickMove(moves))
                }
            }

            traversalList
        }
    }

    // A "heuristic" for picking movements random shuffle and get the first
    private fun pickMove(moves: List<Action>): Action {
        return moves.shuffled().first()
    }
}
