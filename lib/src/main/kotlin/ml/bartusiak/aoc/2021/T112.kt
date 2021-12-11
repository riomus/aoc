package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1
open class T112: T111(){

    companion object {
        fun solve(file: String = "T11.txt"): Int = T112().solve2(file)
    }


    fun solve2(file: String): Int {
        val squids: Map<Int, Map<Int, Int>> = extractMap(file)
        val result =  generateSequence(Pair(squids, 0)) { nextSquids(it) }.mapIndexed { index, pair -> Pair(index, pair.first) }.find { (_, map) -> map.values.flatMap { it.values }.all { it==0 } }
        return result!!.first
    }


}




