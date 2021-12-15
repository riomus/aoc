package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1
open class T152: T151(){

    companion object {
        fun solve(file: String = "T15.txt"): Long = T152().solve(file)
    }
    operator fun Pair<Int, Int>.times(other:Int): Pair<Int, Int>{
        return Pair(this.first*other, this.second*other)
    }
    override fun solve(file: String): Long {
        val map: Map<Pair<Int, Int>, Int> = loadMap(file)
        val largeMap = makeMapLarge(map)
        val xSize = largeMap.keys.maxOf { it.first }
        val ySize = largeMap.keys.maxOf { it.second }
        val endNode = Pair(xSize, ySize)
        val startNode = Pair(0,0)
        return djikstra(startNode, endNode, largeMap)
    }

    fun makeMapLarge(map: Map<Pair<Int, Int>, Int>): Map<Pair<Int, Int>, Int> {
        val xSizeSmall = map.keys.maxOf { it.first }
        val ySizeSmall = map.keys.maxOf { it.second }
        val largeMap = map.entries.flatMap { (key, value) ->
            (1..4).map { increase -> Pair(key + (Pair(xSizeSmall + 1, 0) * increase),  (value-1 + increase)%9 +1) }+Pair(key,value)
        }.flatMap { (key, value) ->
            (1..4).map { increase -> Pair(key + (Pair(0, ySizeSmall + 1) * increase), (value-1 + increase) %9 + 1) }+Pair(key,value)
        }.toMap()
        return largeMap
    }

}




