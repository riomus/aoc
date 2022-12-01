package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*

data class DjikstraState(
    val unvisitedQueue: PriorityQueue<Pair<Pair<Int, Int>, Long>>, val unvisitedNodes: MutableSet<Pair<Int, Int>>,
    val distances: MutableMap<Pair<Int, Int>, Long>, val currentNode: Pair<Int, Int>?
)

open class T151 : AOCTask {

    val moves: Sequence<Pair<Int, Int>> =
        listOf(Pair(1, 0), Pair(0, 1)).flatMap { listOf(it, Pair(it.first * -1, it.second * -1)) }.asSequence()

    companion object {
        fun solve(file: String = "2021/T15.txt"): Long = T151().solve(file)
    }

    open fun solve(file: String): Long {
        val map: Map<Pair<Int, Int>, Int> = loadMap(file)
        val xSize = map.keys.maxOf { it.first }
        val ySize = map.keys.maxOf { it.second }
        val endNode = Pair(xSize, ySize)
        val startNode = Pair(0, 0)
        return djikstra(startNode, endNode, map)
    }

    fun loadMap(file: String) = data(file)
        .map { it.toCharArray().map { it.toString().toInt() } }
        .flatMapIndexed { row, rowData -> rowData.mapIndexed { column, risk -> Pair(Pair(row, column), risk) } }
        .toMap()

    fun djikstra(startNode: Pair<Int, Int>, endNode: Pair<Int, Int>, map: Map<Pair<Int, Int>, Int>): Long {
        val startDistances = map.mapValues { Long.MAX_VALUE } + Pair(startNode, 0L)
        val compareByDistance: Comparator<Pair<Pair<Int, Int>, Long>> = compareBy { it.second }
        val unvisitedQueue = PriorityQueue(compareByDistance)
        val initialNode = Pair(0, 0)
        val thisDjikstraStep = { state: DjikstraState -> djijkstraStep(map, state) }
        val solution = generateSequence(
            DjikstraState(
                unvisitedQueue,
                map.keys.toMutableSet(),
                startDistances.toMutableMap(),
                initialNode
            ), thisDjikstraStep
        ).dropWhile { state -> state.unvisitedNodes.contains(endNode) }.first()
        return solution.distances[endNode]!!
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    fun djijkstraStep(map: Map<Pair<Int, Int>, Int>, state: DjikstraState): DjikstraState {
        val (unvisitedQueue, unvisitedSet, distances: MutableMap<Pair<Int, Int>, Long>, currentNode) = state
        return if (currentNode != null) {
            val unvisitedNeighbours =
                moves.map { it + currentNode }.filter { distances.contains(it) }.filter { unvisitedSet.contains(it) }
            val currentDistance = distances[currentNode]!!
            val unvisitedDistances = unvisitedNeighbours.map { node ->
                val edgeWeight = map[node]!!
                Pair(node, edgeWeight + currentDistance)
            }.filter { it.second < distances[it.first]!! }
            unvisitedDistances.forEach { pair ->
                unvisitedQueue.add(pair)
                distances[pair.first] = pair.second
            }
            unvisitedSet.remove(currentNode)

            DjikstraState(unvisitedQueue, unvisitedSet, distances, unvisitedQueue.poll()?.first)
        } else {
            state
        }
    }


}




