package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

object T51: AOCTask{

    data class Line(val x0: Int, val y0: Int, val x1: Int, val y1: Int){

        fun points(includeDiagonal: Boolean = false): List<Pair<Int, Int>> {
            return if(x0==x1 || y0==y1 || includeDiagonal){
                val steps = max(abs(x0-x1), abs(y0-y1))
                val xDiffs= abs(x0-x1)/(steps.toDouble())
                val yDiffs = abs(y0-y1)/(steps.toDouble())
                val xDirection = sign((x1-x0).toDouble())
                val yDirection = sign((y1-y0).toDouble())
                val points = (0 until steps+1).map { step ->
                    Pair((x0+step*xDiffs*xDirection).toInt(), (y0+step*yDiffs*yDirection).toInt())
                }
            points
        } else{
            listOf()
            }
        }

    }

    fun solve(file: String = "T5.txt", includeDiagonal: Boolean = false): Int {
        val ventLines: List<Line> = data(file).map{ line ->
            val lineData = line.split(" -> ").map{ linePart -> linePart.split(",").map{it.toInt()}}
            Line(lineData[0][0], lineData[0][1], lineData[1][0], lineData[1][1])
        }

        val result: Map<Int, Map<Int, Int>> = ventLines.flatMap { it.points(includeDiagonal) }.fold(mapOf<Int,Map<Int, Int>>()) { map, point ->
            val column = map.getOrDefault(point.first, mapOf<Int, Int>())
            val currentValue = column.getOrDefault(point.second, 0)
            map + Pair(point.first, (column + Pair(point.second, currentValue + 1)))
        }

        return result.values.flatMap { it.values }.count { it>1 }
    }


}