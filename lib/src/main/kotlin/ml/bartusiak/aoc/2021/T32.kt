package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs

object T32 : AOCTask {

    data class Line(val direction: String, val distance: Int)

    fun solve(file: String = "2021/T3.txt"): Int {
        val lines: List<List<Int>> = data(file).map {
            it.toList().map(Char::digitToInt)
        }
        val oxygen = filterLines(lines, 0, 0)
        val co2 = filterLines(lines, 0, 1)
        return oxygen.joinToString("").toInt(2) * co2.joinToString("").toInt(2)
    }

    private tailrec fun filterLines(lines: List<List<Int>>, index: Int, modifier: Int): List<Int> {
        if (lines.size == 1) {
            return lines.first()
        }
        val counts = lines.fold(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) { acc, line ->
            line.zip(acc).map {
                val (acc, v) = it
                acc + v
            }
        }
        val size = lines.size
        val cryteria = counts.map {
            if (it >= (size / 2.0)) {
                abs(1 - modifier)
            } else {
                abs(0 - modifier)
            }
        }
        return filterLines(lines.filter { it[index] == cryteria[index] }, index + 1, modifier)
    }

}