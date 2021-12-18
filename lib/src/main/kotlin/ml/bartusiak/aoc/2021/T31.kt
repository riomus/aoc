package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs

object T31 : AOCTask {

    data class Line(val direction: String, val distance: Int)

    fun solve(): Int {
        val lines = data("T3.txt").map {
            it.toList().map(Char::digitToInt)
        }
        val size = lines.size
        val counts = lines.fold(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) { acc, line ->
            line.zip(acc).map {
                val (acc, v) = it
                acc + v
            }
        }
        val gamma = counts.map {
            if (it > size / 2.0) {
                1
            } else {
                0
            }
        }
        val epsilon = gamma.map {
            abs(it - 1)
        }

        return gamma.joinToString("").toInt(2) * epsilon.joinToString("").toInt(2)
    }

}