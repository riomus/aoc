package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
interface T1: AOCTask  {
    fun loadData(path: String): List<List<Int>> = data("2022/T11.txt").fold(mutableListOf(mutableListOf<Int>())){ acc, line ->
        if(line.isEmpty()){
            acc.add(mutableListOf())
            acc
        }else {
            acc.last().add(line.toInt())
            acc
        }
    }
}
object T11 : T1 {

    fun solve(): Int {
        val data = loadData("2022/T11.txt")
        return data.maxOf { it.sum() }


    }

}