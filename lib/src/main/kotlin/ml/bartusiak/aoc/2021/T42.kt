package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

object T42 : AOCTask {

    fun solve(file: String = "T4.txt"): Int {
        val lines = data(file)
        val draws = lines.first().split(",").map(String::toInt)
        val loadedBoards: List<Board> = lines.drop(1).filter { it.isNotEmpty() }.chunked(5).map { it ->
            val rows = it.map { row -> row.split(" ").filter { it.isNotEmpty() }.map { Pair(it.toInt(), false) } }
            Board(rows)
        }
        val (_: List<Board>, _: List<Int>, winningOrder: List<Pair<Board, Int>>) = draws.fold(
            Triple(
                loadedBoards, listOf<Int>(),
                listOf<Pair<Board, Int>>()
            )
        ) { acc, i ->
            val (boards, alreadyDrawn, winning) = acc
            val newWinning = winning.toMutableList()
            boards.filter { it.bingo() }.forEach { board ->
                if (!winning.map { it.first }.contains(board)) {
                    newWinning.add(Pair(board, alreadyDrawn.last()))
                }
            }
            val data = alreadyDrawn.toMutableList()
            data.add(i)
            Triple(
                boards.map { board ->
                    board.draw(i)
                }, data, newWinning
            )
        }
        val (lastWinning, lastWinningDraw) = winningOrder.last()
        return (lastWinning.score()) * lastWinningDraw
    }


}