package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask


object T41 : AOCTask {

    fun IntRange.contains(other: IntRange): Boolean{
        return other.first >= this.first && other.endInclusive <= this.endInclusive
    }
    fun solve(file: String = "2022/T31.txt"): Int {
        return data(file).map{line ->
           val pair = line.split(",")
               .map{it.split("-").map{it.toInt()}
               }
            Pair(pair[0][0] .. pair[0][1],pair[1][0] .. pair[1][1])
        }.count { (p1, p2) ->
            p1.contains(p2) || p2.contains(p1)
        }

    }

}