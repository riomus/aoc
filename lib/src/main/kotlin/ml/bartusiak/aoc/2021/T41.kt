package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

object T41 : AOCTask {


    fun solve(file: String = "2021/T4.txt"): Int {
        val lines = data(file)
        val draws = lines.first().split(",").map(String::toInt)
        val loadedBoards: List<Board> = lines.drop(1).filter { it.isNotEmpty() }.chunked(5).map { it ->
            val rows = it.map { row -> row.split(" ").filter { it.isNotEmpty() }.map { Pair(it.toInt(), false) } }
            Board(rows)
        }
        val (boards: List<Board>, drawns: List<Int>) = draws.fold(Pair(loadedBoards, listOf<Int>())) { acc, i ->
            val (boards, alreadyDrawn) = acc
            if (boards.any { it.bingo() }) {
                acc
            } else {
                val data = alreadyDrawn.toMutableList()
                data.add(i)
                Pair(
                    boards.map { board ->
                        board.draw(i)
                    }, data
                )
            }
        }
        return (boards.map { it.score() }.maxOrNull() ?: 0) * drawns.last()
    }


}