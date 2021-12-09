package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1
object T82: AOCTask{

    val uniques = mapOf<Int, Int>(1 to 2, 4 to 4, 7 to 3, 8 to 7)
    val sizeToDigit = uniques.entries.associate{(k,v)-> v to k}

    fun solve(file: String = "T8.txt"): Int {
        val observations = data(file).map { it.trim().split("|").map{ it.trim().split(" ").map{it.trim().toCharArray().map{it.toString()}}.map{it.toSet()}}}.map{Pair(it[0], it[1])}
            return observations.map { pair ->
                var codes = pair.first.toMutableList()
                var display = pair.second.toMutableList()
                val known = codes.flatMap { code ->
                    sizeToDigit[code.size]?.let{ digit ->
                        listOf(code to digit)
                    }?: listOf()
                }.associate { pair -> pair }
                var knownNumbers = known.entries.associate { it.value to it.key }.toMutableMap()
                codes.removeAll(knownNumbers.values)
                knownNumbers[9] = codes.find {  code ->
                    code.size== 6 &&(code.subtract(knownNumbers[7]!!).subtract(knownNumbers[4]!!)).size == 1
                }!!
                codes.remove(knownNumbers[9])
                knownNumbers[0] = codes.find { it.size==6 && (it.subtract(knownNumbers[1]!!)).size == 4 }!!
                codes.remove(knownNumbers[0])
                knownNumbers[6] = codes.find { it.size==6 && (it.subtract(knownNumbers[1]!!)).size == 5 }!!
                codes.remove(knownNumbers[6])
                knownNumbers[3] = codes.find { it.size==5 && (it.subtract(knownNumbers[1]!!)).size == 3 }!!
                codes.remove(knownNumbers[3])
                knownNumbers[2] = codes.find { it.size==5 && (it.subtract(knownNumbers[4]!!)).size == 3 }!!
                codes.remove(knownNumbers[2])
                knownNumbers[5] = codes.find { it.size==5 }!!
                codes.remove(knownNumbers[5])

                val codesMap = knownNumbers.entries.associate { it.value to it.key }
                display.mapIndexed{index, data ->
                    (codesMap[data]!!.toDouble()*pow(10.0, 3.0-index)).toInt()
                }.sum()
            }.sum()
    }
}




