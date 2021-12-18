package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

object T81 : AOCTask {

    val uniques = mapOf<Int, Int>(1 to 2, 4 to 4, 7 to 3, 8 to 7)
    val uniqueSizes = uniques.values.toSet()

    fun solve(file: String = "T8.txt"): Int {
        val observations = data(file).map {
            it.trim().split("|").map { it.trim().split(" ").map { it.trim().toCharArray().map { it.toString() } } }
        }.map { Pair(it[0], it[1]) }
        return observations.flatMap { it.second.map { it.size } }.count { uniqueSizes.contains(it) }
    }
}




