package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

open class T152 : T151() {

    override fun solve(file: String, searchSpace: Long): Long{
        val pairs = beacons(file)
        val (x: Long, y: Long) = (0L..searchSpace).firstNotNullOf { y ->
            val rowPositions = pairs.mapNotNull { it.notPresentBeaconsAt(y) }.sortedBy { it.first }
            val reducedPositions = rowPositions.fold(mutableListOf(rowPositions.first())){ acc, longRange ->
                val lastRange = acc.last()
                if(longRange.last>lastRange.last){
                    acc.add(longRange)
                }
                acc
            }.windowed(2,1)
            reducedPositions.firstNotNullOfOrNull { (r1, r2) ->
                if((r2.first-r1.last) >0){
                    r2.first-1 to y
                } else {
                    null
                }
            }
        }
        return (x+1)*4000000+y

    }


}