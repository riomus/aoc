package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Range(val from: Long, val to: Long) {
    val range = from..to
    fun contains(n: Long ): Boolean = n in range
    fun overlap(other: Range): Range = Range(max(from, other.from),min(to, other.to))
    val length = abs(to-from)+1
}

data class RebootStep(val instruction: String, val x: Range, val y: Range, val z: Range)

typealias Point = Triple<Long, Long, Long>
open class T221 : AOCTask {

    companion object {
        fun solve(file: String = "2021/T22.txt"): Long = T221().solve(file)
    }


    fun Set<Point>.inRange(xRange:Range, yRange: Range, zRange: Range): Int {
        return this.count { (x, y, z) ->
            xRange.contains(x) && yRange.contains(y) && zRange.contains(z)
        }
    }

    open fun solve(file: String): Long {
        val instructions = loadInstructions(file)
        val range = Range(-50,50)
        val world = buildWorld(instructions, Triple(range, range, range))

        return world.size.toLong()
    }

    fun buildWorld(instructions: List<RebootStep>, area: Triple<Range, Range, Range>): Set<Point> {
        val (xRange, yRange, zRange) = area
        return instructions.foldIndexed(mutableSetOf()) { indx, acc, instr ->
            val operation: (Point) -> Boolean =
                if (instr.instruction == "on") { n -> acc.add(n) } else { n -> acc.remove(n) }
            instr.x.range.filter{xRange.contains(it)}.flatMap { x ->
                instr.y.range.filter{yRange.contains(it)}.flatMap { y ->
                    instr.z.range.filter{zRange.contains(it)}.map { z ->
                        operation(Triple(x, y, z))
                    }
                }
            }
            acc
        }
    }

    fun loadInstructions(file: String): List<RebootStep> {
        val lines = data(file)
        return lines.map { line ->
            val firstSplit = line.split(" ")
            val instruction = firstSplit.first()
            val ranges = firstSplit.last().split(",").map { encoded ->
                val range = encoded.split("=").last().split("..").map { it.toLong() }
                Range(range.first(), range.last())
            }
            RebootStep(instruction, ranges[0], ranges[1], ranges[2])
        }
    }

}



