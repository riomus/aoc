package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
object T21 : AOCTask {

    enum class Move{
        Rock, Paper, Scissors;

        fun winnings() = setOf ((Rock to Scissors), (Scissors to Paper), (Paper to  Rock))
        fun points() = when(this) {
            Rock -> 1
            Paper -> 2
            Scissors -> 3
        }
        fun bets(that: Move) = winnings().contains((this to that))
    }

    private fun Pair<Move, Move>.points(): Int {
        val shapeScore = this.second.points()
        val winPoint =  if(this.second.bets(this.first)){
            6
        } else if (this.second == this.first){
            3
        } else {
            0
        }
        return shapeScore + winPoint
    }

    private val opponentMap: Map<String, Move> = mapOf(("A" to  Move.Rock), ("B" to  Move.Paper), ("C" to  Move.Scissors))
    private val myMap: Map<String, Move> = mapOf(("X" to  Move.Rock), ("Y" to  Move.Paper), ("Z" to  Move.Scissors))
    fun solve(file: String = "2022/T21.txt"): Int {
        val data = data(file).map{row ->
            val moves = row.split(" ")
            (opponentMap[moves[0]]!! to myMap[moves[1]]!!)
        }.map { game ->
            game.points()
        }
        return data.sum()
    }

}