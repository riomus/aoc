package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
object T31 : AOCTask {

    val prios: Map<Char, Int> = ((('a' .. 'z').map { it to (it - 96) }) + (('A' .. 'Z').map { it to (it - 38) })).toMap().mapValues {it.value.code }
    fun solve(file: String = "2022/T31.txt"): Int {
        val data = data(file).flatMap{row ->
           row.chunked(row.length/2).map {
               it.toCharArray().toSet()
           }.reduce{ a, b ->
               a.intersect(b)
           }
        }.map {
            prios[it]!!
        }
        return data.sum()
    }

}