package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


open class T242 : T241() {

    companion object {
        fun solve(file: String = "2021/T24.txt"): Long = T242().solve(file)
    }


    override fun solve(file: String): Long{
        val instructions: Instructions = loadInstructions(file)
        val solution = findSolution(instructions, (1 .. 9).asSequence())
        return solution
    }


}



