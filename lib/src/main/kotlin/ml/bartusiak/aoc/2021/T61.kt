package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1
object T61: AOCTask{

    fun solve(file: String = "T6.txt", days: Int = 80): Long {
        val population: Map<Int, Long> = data(file).flatMap{ line -> line.split(",")}.map{it.toInt()}.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        val result =  (0 until days).fold(population){ acc: Map<Int, Long>, _: Int ->
            val newPopulation = acc.flatMap { entry ->
                if(entry.key==0){
                    listOf(Pair(8, entry.value), Pair(6, entry.value))
                } else {
                    listOf(Pair(entry.key-1, entry.value))
                }
            }
            newPopulation.groupingBy { it.first }.fold(0L){acc, element ->
                acc+element.second
            }
        }

        return result.values.sum()
        }
}




