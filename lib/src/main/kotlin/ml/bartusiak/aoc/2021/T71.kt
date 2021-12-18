package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs

object T71 : AOCTask {

    fun solve(file: String = "T7.txt"): Int {
        val crabs = data(file).flatMap { it.split(",") }.map { it.toInt() }
        return crabs.toSet().map { crab ->
            crabs.sumOf { abs(crab - it) }
        }.minOrNull()!!
    }
}




