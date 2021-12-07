package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1
object T71: AOCTask{

    fun solve(file: String = "T7.txt"): Int {
        val crabs = data(file).flatMap { it.split(",") }.map { it.toInt() }
            return crabs.toSet().map{ crab ->
                crabs.sumOf { abs(crab - it) }
            }.minOrNull()!!
    }
}




