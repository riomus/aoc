package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1

open class T172 : T171() {

    companion object {
        fun solve(file: String = "T17.txt"): Long = T172().solve(file)
    }

    override fun solve(file: String): Long {
        val area: Area= loadArea(file)
        val initStates = findSteps(area).map {it.initialState }.toSet()
        return initStates.size.toLong()
    }



}




