package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1

open class T141 : AOCTask {

    companion object {
        fun solve(file: String = "T14.txt"): Long = T141().solve(file, 10)
    }


    open fun solve(file: String, steps: Int): Long {
        val dataLines = data(file)
        val polymer = dataLines.first().toCharArray().map { it.toString() }
        val instructions: Map<String, String> = dataLines.drop(2).map {
            val extracted = it.split("->").map { it.trim() }
            Pair(extracted[0], extracted[1])
        }.toMap()
        val result: Long = count(polymer, instructions, steps)
        return result
    }

    private fun count(polymer: List<String>, instructions: Map<String, String>, steps: Int): Long {
        val elementsCounts: Map<String, Long> = polymer.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        val ruleCounts: Map<String, Long> =
            polymer.windowed(2, 1).groupingBy { it.joinToString("") }.eachCount().mapValues { it.value.toLong() }
        val counters = generateSequence(elementsCounts + ruleCounts) { currentCounters ->
            val newCounts = instructions.entries.fold(mapOf<String, Long>()) { currentStepCounters, instruction ->
                val firstKey = instruction.key.first().toString() + instruction.value
                val secondKey = instruction.value + instruction.key.last().toString()
                currentStepCounters +
                        Pair(firstKey, (currentStepCounters[firstKey] ?: 0L) + (currentCounters[instruction.key] ?: 0L)) +
                        Pair(secondKey, (currentStepCounters[secondKey] ?: 0L) + (currentCounters[instruction.key] ?: 0L)) +
                        Pair(
                            instruction.value,
                            (currentStepCounters[instruction.value] ?: 0L) + (currentCounters[instruction.key] ?: 0L)
                        )
            }
            newCounts + currentCounters.filter { it.key.length == 1 }.mapValues { it.value + (newCounts[it.key] ?: 0L) }
        }.drop(steps).first()
        val counts = counters.entries.filter { it.key.length < 2 }.map { it.value }
        return (counts.maxOrNull()!!) - counts.minOrNull()!!
    }

}




