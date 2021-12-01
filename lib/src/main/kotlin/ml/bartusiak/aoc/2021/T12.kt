package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

object T12: AOCTask{

    fun solve(): Int {
        return data("T1.txt").map{
            it.toInt()
        }.windowed(3)
                .map{it.sum()}
                .windowed(2).count{
                    it[0]<it[1]
                }
    }

}