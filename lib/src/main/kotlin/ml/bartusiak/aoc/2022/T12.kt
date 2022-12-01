package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask



object T12 : T1 {

    fun solve(): Int {
        val data = loadData("2022/T11.txt").map { it.sum() }.sortedDescending()
        return data.take(3).sum()


    }

}