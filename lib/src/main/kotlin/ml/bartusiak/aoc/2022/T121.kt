package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.RuntimeException
import java.util.Comparator
import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min


open class T121 : AOCTask  {


    val moves = listOf(
        1 to 0,
        -1 to 0,
        0 to -1,
        0 to 1
    )

    open fun solve(file: String): Long {
        val map = loadMap(file)
        val result = djikstra(map)
        return result
    }

    fun loadMap(file: String): TaskMap {
        val board = loadBoard(file)
        val start = board.first { it.second=='S'.code }.first
        val end = board.first { it.second=='E'.code }.first
        return TaskMap(
            start,
            end,
            board.toMap() + (start to 'a'.code) + (end to 'z'.code)
        )
    }

    fun loadBoard(file: String): List<Pair<Pair<Int, Int>, Int>> {
        val board = data(file).map { line -> line.split("").filter { it.isNotEmpty() } }.flatMapIndexed { row, line ->
            line.mapIndexed { col, elev ->
                (row to col) to elev[0].code
            }
        }
        return board
    }

    data class TaskMap(val start: Pair<Int, Int>, val end: Pair<Int, Int>, val elevations: Map<Pair<Int, Int>, Int>)


    data class Node(val cords: Pair<Int, Int>, val distance: Long)

    data class DjikstraState(
        val unvisitedQueue: PriorityQueue<Node>, val visitedNodes: MutableSet<Pair<Int, Int>>,
        val distances: MutableMap<Pair<Int, Int>, Long>, val currentNode: Node?, val map: TaskMap
    )

    fun djikstra(map: TaskMap): Long {
        val startDistances = mutableMapOf<Pair<Int, Int>, Long>() + Pair(map.start, 0L)
        val compareByDistance: Comparator<Node> = compareBy { it.distance }
        val unvisitedQueue = PriorityQueue(compareByDistance)
        val thisDjikstraStep = { state: DjikstraState -> djijkstraStep(state) }
        val solution = generateSequence(
            DjikstraState(
                unvisitedQueue,
                mutableSetOf(),
                startDistances.toMutableMap(),
                Node(map.start, 0),
                map
            ), thisDjikstraStep
        ).drop(1).dropWhile {
                state -> (!state.visitedNodes.contains(map.end)) && state.currentNode!=null
        }.firstOrNull()
        return solution?.let { it.distances[map.end]} ?: Long.MAX_VALUE
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    fun djijkstraStep(state: DjikstraState): DjikstraState {
        val (unvisitedQueue, visitedCords, distances: MutableMap<Pair<Int, Int>, Long>, currentNode, taskMap) = state
        return if (currentNode != null) {

            val unvisitedNeighbours: List<Node> = moves.map { it+currentNode.cords }.map { Node(it, currentNode.distance+1) }.filter { taskMap.elevations.containsKey(it.cords) }.filter { node ->
                (taskMap.elevations[node.cords]!!-taskMap.elevations[currentNode.cords]!!)<=1
            }
            val unvisitedDistances = unvisitedNeighbours.filter { it.distance < distances[it.cords]?:Long.MAX_VALUE }
            unvisitedDistances.forEach { pair ->
                unvisitedQueue.add(pair)
                distances[pair.cords] = pair.distance
            }
            visitedCords.add(currentNode.cords)

            DjikstraState(unvisitedQueue, visitedCords, distances, unvisitedQueue.poll(), taskMap)
        } else {
            state
        }
    }
}