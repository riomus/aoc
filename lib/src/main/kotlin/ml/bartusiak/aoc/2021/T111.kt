package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

import kotlin.reflect.KFunction1
open class T111: AOCTask{

    companion object {
        fun solve(file: String = "T11.txt", steps: Int = 100): Int = T111().solve(file ,steps)
    }


    val moves = listOf(Pair(1,0), Pair(0,1), Pair(1,1), Pair(-1,1)).flatMap { listOf(it, Pair(it.first*-1, it.second*-1)) }
    open fun solve(file: String, steps:Int): Int {
        val squids: Map<Int, Map<Int, Int>> = extractMap(file)
        val result =  generateSequence(Pair(squids, 0)) { nextSquids(it) }.drop(steps).first()
        return result.second
    }


     fun nextSquids(state:Pair<Map<Int, Map<Int, Int>>, Int>): Pair<Map<Int, Map<Int, Int>>, Int> {
        val (map, count) = state
        val newMap = map.mapValues { it.value.mapValues { (it.value+1)%10 }}
         val startZeros = newMap.entries.flatMap { (x, row) -> row.entries.filter { (_, v) -> v==0 }.map { (y, _) -> Pair(x,y) } }
        val step2Map: Map<Int, Map<Int, Int>> = step2(newMap, startZeros)
         val newZeros = step2Map.values.flatMap { it.values }.count { it==0 }+count
        return Pair(step2Map, newZeros)
    }

     tailrec fun step2(map:Map<Int, Map<Int, Int>>, zeros: List<Pair<Int, Int>>): Map<Int, Map<Int, Int>> {
         return if(zeros.isEmpty()){
             map
         } else {
             val increments: Map<Pair<Int, Int>, Int> = zeros.flatMap { (x, y) ->
                 moves.map { (dx, dy)  -> Pair(x+dx, y+dy) }
             }.filter { (x, y) -> x>-1 && y>-1 && x<map.size && y<map[x]!!.size }.groupingBy { it }.eachCount()
             val newMap: Map<Int, Map<Int, Int>> = increments.entries.filter { entry -> map[entry.key.first]!![entry.key.second]!!>0 }.fold(map){ lastMap, entry ->
                 val (x,y)= entry.key
                 val inc = entry.value
                 val incrementedValue = lastMap[x]!![y]!! + inc
                 val newMap = lastMap + Pair(x, lastMap[x]!! + Pair(y, if (incrementedValue>9) 0 else incrementedValue))
                 newMap
             }
             val newZeros: List<Pair<Int, Int>> = newMap.entries.flatMap { (x, row) -> row.entries.flatMap{ (y, value) ->
                 if(map[x]!![y]!! != 0 && value == 0){
                     listOf(Pair(x,y))
                } else {
                    listOf()
                 }
             } }
             step2(newMap, newZeros)
         }
     }



    fun extractMap(file: String) = data(file)
        .map { it.trim().toCharArray().map { it.toString().toInt() } }
        .mapIndexed { rowId, elements ->
            rowId to elements.mapIndexed { colId, element ->
                colId to element
            }.associate { it }
        }.associate { it }

}




