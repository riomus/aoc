package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

object T22 : AOCTask {

    data class Line(val direction: String, val distance: Int)

    fun solve(): Int {
        val (depth, horizontal, _) = data("T2.txt").map {
            val data = it.split(" ")
            Line(data[0], data[1].toInt())
        }.fold(Triple(0, 0, 0)) { acc, line ->
            when (line.direction) {
                "forward" -> acc.copy(
                    first = acc.first + acc.third * line.distance,
                    second = acc.second + line.distance
                )
                "up" -> acc.copy(third = acc.third - line.distance)
                else -> acc.copy(third = acc.third + line.distance)
            }
        }
        return depth * horizontal
    }

}