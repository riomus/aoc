package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


open class T231 : AOCTask {

    companion object {
        fun solve(file: String = "T23.txt"): Long = T231().solve(file)
    }

    val A = Amiphipod("A", 1L)
    val B = Amiphipod("B", 10L)
    val C = Amiphipod("C", 100L)
    val D = Amiphipod("D", 1000L)

    fun getAmiphipod(kind: String): Amiphipod{
        return when (kind){
            "A" -> A
            "B" -> B
            "C" -> C
            "D" -> D
            else -> throw Exception("What is that!")
        }
    }

    open fun solve(file: String): Long {
        val startState = loadGameState(file)
        val finalState = GameState(mapOf(),  mapOf(2 to (0 until startState.roomsSize).map { Pair(it, A) }.toMap(),
            4 to (0 until startState.roomsSize).map { Pair(it, B) }.toMap(),
            6 to (0 until startState.roomsSize).map { Pair(it, C) }.toMap(),
            8 to (0 until startState.roomsSize).map { Pair(it, D) }.toMap()), startState.roomsSize)
        val result = djikstra(startState, finalState)
        return result
    }

    private fun loadGameState(file: String): GameState {
        val lines = data(file).drop(2).dropLast(1).map { it.replace("#","").trim() }.map{it.toCharArray().map { getAmiphipod(it.toString()) }}
        val roomSize = lines.size
        val rooms = listOf(2,4,6,8).map{room ->
            Pair(room, (0 until roomSize).map { idx  -> Pair(idx, lines[idx][room/2-1])}.toMap())
        }.toMap()
        return GameState(mapOf(), rooms, roomSize)
    }

    data class Amiphipod(val kind: String, val moveCost: Long)


    data class Node(val state: GameState, val distance: Long){
        fun generateNeighbourNodes(): Set<Node>{
            val hallwaySpots = listOf(0, 1, 3, 5, 7, 9, 10)
            val destinations = mapOf("A" to 2, "B" to 4, "C" to 6, "D" to 8)
            //From room to hallway and room to room
            val roomMoves: Set<Node> = state.rooms.entries.flatMap{ col: Map.Entry<Int, Map<Int, Amiphipod>> ->
                val x = col.key
                col.value.entries.flatMap { row: Map.Entry<Int, Amiphipod> ->
                    val y = row.key
                    val amiphipod = row.value
                    val destination = destinations[amiphipod.kind]!!
                    val roomsWithoutIt = state.rooms+Pair(x, col.value-y)
                    if(x==destination && col.value.values.all { it.kind==amiphipod.kind }){ // already is where should be
                        listOf()
                    } else if(state.rooms[destination]!!.map { it.value }.all { amp -> amp.kind==amiphipod.kind}) { // can go to destination
                        val isBlockedOnHallwayPath = (min(destination, x)..max(destination, x)).any{state.hallway.get(it)!=null}
                        val isBlockedOnColumnPath = (0..y).any{(col.value[it]?.kind?:amiphipod.kind)!=amiphipod.kind}
                        if(isBlockedOnHallwayPath||isBlockedOnColumnPath){
                            listOf()
                        } else {
                            (0..state.roomsSize-1).filter{roomsWithoutIt[destination]!![it]==null}.map{ yDestination ->
                                val pathDistance: Long = (y+1+abs(destination-x)+yDestination+1)*amiphipod.moveCost
                                Node(GameState(state.hallway, roomsWithoutIt+Pair(destination, roomsWithoutIt[destination]!!+Pair(yDestination, amiphipod)), state.roomsSize), distance+pathDistance)
                            }
                        }
                    }else {
                        hallwaySpots.flatMap {  possibleDestination ->
                            val isBlockedOnHallwayPath = (min(possibleDestination, x)..max(possibleDestination, x)).any{state.hallway.get(it)!=null}
                            val isBlockedOnColumnPath = (0..y).any{(col.value[it]?.kind?:amiphipod.kind)!=amiphipod.kind}
                            if(isBlockedOnHallwayPath||isBlockedOnColumnPath){
                                listOf()
                            } else {
                                val pathDistance: Long = (y+1+abs(possibleDestination-x))*amiphipod.moveCost
                                listOf(Node(GameState(state.hallway+Pair(possibleDestination, amiphipod), roomsWithoutIt, state.roomsSize), distance+pathDistance))
                            }
                        }
                    }
                }
            }.toSet()
            //From hallway to room
            val hallwayMoves: Set<Node> = state.hallway.entries.flatMap { hallwaySpot ->
                val x = hallwaySpot.key
                val amiphipod = hallwaySpot.value
                val destination = destinations[amiphipod.kind]!!
                val isBlockedOnHallwayPath = (min(destination, x+1)..max(destination, x-1)).any{state.hallway.get(it)!=null}
                val isBlockedOnColumnPath = (0..state.roomsSize).any{(state.rooms[destination]!![it]?.kind?:amiphipod.kind)!=amiphipod.kind}
                if(isBlockedOnHallwayPath||isBlockedOnColumnPath){
                    listOf()
                } else {
                    (0..state.roomsSize-1).filter{state.rooms[destination]!![it]==null}.map{ y ->
                        val pathDistance: Long = (y+1+abs(destination-x))*amiphipod.moveCost
                        Node(GameState(state.hallway-x, state.rooms+Pair(destination, state.rooms[destination]!!+Pair(y, amiphipod)), state.roomsSize), distance+pathDistance)
                    }
                }
            }.toSet()
            return hallwayMoves+roomMoves
        }
    }
    data class GameState(val hallway: Map<Int, Amiphipod>, val rooms: Map<Int, Map<Int, Amiphipod>>, val roomsSize: Int)



    data class DjikstraState(
        val unvisitedQueue: PriorityQueue<Node>, val visitedStates: MutableSet<GameState>,
        val distances: MutableMap<GameState, Long>, val currentNode: Node?
    )

    fun djikstra(startState: GameState, endNode: GameState): Long {
        val startDistances = mutableMapOf<GameState, Long>() + Pair(startState, 0L)
        val compareByDistance: Comparator<Node> = compareBy { it.distance }
        val unvisitedQueue = PriorityQueue(compareByDistance)
        val thisDjikstraStep = { state: DjikstraState -> djijkstraStep(state) }
        val solution = generateSequence(
            DjikstraState(
                unvisitedQueue,
                mutableSetOf(),
                startDistances.toMutableMap(),
                Node(startState, 0)
            ), thisDjikstraStep
        ).dropWhile { state -> !state.visitedStates.contains(endNode) }.first()
        return solution.distances[endNode]!!
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    fun djijkstraStep(state: DjikstraState): DjikstraState {
        val (unvisitedQueue, visitedStates, distances: MutableMap<GameState, Long>, currentNode) = state
        return if (currentNode != null) {

            val unvisitedNeighbours: Set<Node> = currentNode.generateNeighbourNodes()
            val unvisitedDistances = unvisitedNeighbours.filter { it.distance < distances[it.state]?:Long.MAX_VALUE }
            unvisitedDistances.forEach { pair ->
                unvisitedQueue.add(pair)
                distances[pair.state] = pair.distance
            }
            visitedStates.add(currentNode.state)

            DjikstraState(unvisitedQueue, visitedStates, distances, unvisitedQueue.poll())
        } else {
            state
        }
    }
}



