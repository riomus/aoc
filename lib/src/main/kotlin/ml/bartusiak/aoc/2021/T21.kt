package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

object T21 : AOCTask {

    data class Line(val direction: String, val distance: Int)

    fun solve(): Int {
        val (depth, horizontal) = data("T2.txt").map {
            val data = it.split(" ")
            Line(data[0], data[1].toInt())
        }.fold(Pair(0, 0)) { acc, line ->
            when (line.direction) {
                "forward" -> acc.copy(second = acc.second + line.distance)
                "up" -> acc.copy(first = acc.first - line.distance)
                else -> acc.copy(first = acc.first + line.distance)
            }
        }
        return depth * horizontal
    }

}