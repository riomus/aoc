package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs

object T72 : AOCTask {

    fun solve(file: String = "2021/T7.txt"): Int {
        val crabs = data(file).flatMap { it.split(",") }.map { it.toInt() }
        val min: Int = crabs.minOrNull()!!
        val max: Int = crabs.maxOrNull()!!
        val values = (min until max).map { value ->
            crabs.sumOf { anotherCrab ->
                val diff = abs(value - anotherCrab)
                val result = (diff * (diff + 1) / 2.0).toInt()
                result
            }
        }
        return values.minOrNull()!!
    }
}




