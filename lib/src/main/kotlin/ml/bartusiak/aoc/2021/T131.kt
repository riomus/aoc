package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

typealias Page = List<Pair<Int, Int>>
typealias Instruction = Pair<String, Int>

open class T131 : AOCTask {


    companion object {
        fun solve(file: String = "2021/T13.txt"): Int = T131().solve(file)
    }


    open fun solve(file: String): Int {
        val loadedData = data(file)
        val points = startPage(loadedData)
        val instructions: List<Pair<String, Int>> = instructions(loadedData)

        val folded = foldThePaper(points, instructions.first())

        return folded.size
    }

    fun startPage(data: List<String>): Page {
        return data
            .filterNot { it.startsWith("f") || it.isEmpty() }
            .map { it.split(",").map { it.toInt() } }
            .map { Pair(it[0], it[1]) }
    }


    fun instructions(data: List<String>): List<Instruction> {
        return data.filter { it.startsWith("f") }
            .map { it.split(" ") }
            .map { it.last() }
            .map { it.split("=") }
            .map { Pair(it[0], it[1].toInt()) }
    }

    fun Instruction.fold(data: Page): Page {
        val selector = if (this.first == "x") {
            Pair<Int, Int>::first
        } else {
            Pair<Int, Int>::second
        }
        val updater = if (this.first == "y") { n: Int, point: Pair<Int, Int> ->
            Pair(
                point.first,
                n
            )
        } else { n: Int, point: Pair<Int, Int> -> Pair(n, point.second) }

        return data.map { point ->
            val pointCoordinate = selector(point)
            val lineCoordinate = this.second
            if (pointCoordinate > lineCoordinate) {
                val newCoordinate = lineCoordinate - (pointCoordinate - lineCoordinate)
                val newPoint = updater(newCoordinate, point)
                newPoint
            } else {
                point
            }
        }.toSet().toList()
    }


    fun foldThePaper(data: Page, instruction: Instruction): Page {
        return instruction.fold(data)
    }

}




