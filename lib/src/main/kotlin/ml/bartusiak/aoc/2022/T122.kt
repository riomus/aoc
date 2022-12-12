package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.RuntimeException
import java.util.Comparator
import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min


open class T122 : T121()  {


    override fun solve(file: String): Long {
        val maps = loadMaps(file)
        val result = maps.minOf { djikstra(it) }
        return result
    }

    fun loadMaps(file: String): List<TaskMap> {
        val board = loadBoard(file)

        val end = board.first { it.second=='E'.code }.first
        val starts = board.filter { it.second=='a'.code ||  it.second=='S'.code}
        return  starts.map { start ->
            TaskMap(
                start.first,
                end,
                board.toMap() + (start.first to 'a'.code) + (end to 'z'.code)
            )
        }
    }

}