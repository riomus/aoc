package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
object T32 : AOCTask {


    fun solve(file: String = "2022/T31.txt"): Int {
        val badgesPrios = data(file).chunked(3).flatMap { group ->
            group
                .map { it.toCharArray().toSet() }
                .reduce{ a,b ->
                    a.intersect(b)
                }.map { T31.prios[it]!! }
        }
        return badgesPrios.sum()
    }

}