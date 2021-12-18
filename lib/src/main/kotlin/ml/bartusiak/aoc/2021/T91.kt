package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

open class T91 : AOCTask {

    companion object {
        fun solve(file: String = "T9.txt"): Int = T91().solve(file)
    }

    val moves = listOf(Pair(1, 0), Pair(0, 1)).flatMap { listOf(it, Pair(it.first * -1, it.second * -1)) }
    open fun solve(file: String): Int {
        val minimas = extractMinimas(extractMap(file))
        return minimas.map { it.third + 1 }.sum()
    }

    fun extractMap(file: String) = data(file)
        .map { it.trim().toCharArray().map { it.toString().toInt() } }
        .mapIndexed { rowId, elements ->
            rowId to elements.mapIndexed { colId, element ->
                colId to element
            }.associate { it }
        }.associate { it }

    fun extractMinimas(data: Map<Int, Map<Int, Int>>): List<Triple<Int, Int, Int>> {
        val minimas = data.entries.flatMap { (rowId, rows) ->
            rows.entries.map { (colId, value) ->
                Triple(rowId, colId, value)
            }
        }.filter { (x, y, value) ->
            moves.map { (dx, dy) ->
                Pair(x + dx, y + dy)
            }.filter { (nextX, nextY) ->
                nextX > -1 && nextY > -1 && nextX < data.size && nextY < data[0]!!.size
            }.all { (newX, newY) ->
                data[newX]!![newY]!! > value
            }
        }
        return minimas
    }
}




