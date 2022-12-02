package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
object T22 : AOCTask {


    enum class GameResult{
        Win, Lose, Draw;

        fun moveToHappen(opponent: T21.Move):T21.Move {
            return if(this == Draw){
                opponent
            } else if (this == Lose) {
                opponent.winnings().toMap()[opponent]!!
            } else {

                opponent.winnings().associate { (it.second to it.first) }[opponent]!!
            }
        }
    }
    private fun Pair<T21.Move, GameResult>.points(): Int {
        val myMove = this.second.moveToHappen(this.first)
        val shapeScore = myMove.points()
        val winPoint =  if(myMove.bets(this.first)){
            6
        } else if (myMove == this.first){
            3
        } else {
            0
        }
        return shapeScore + winPoint
    }

    private val opponentMap: Map<String, T21.Move> = mapOf(("A" to  T21.Move.Rock), ("B" to  T21.Move.Paper), ("C" to  T21.Move.Scissors))
    private val myMap: Map<String, GameResult> = mapOf(("X" to  GameResult.Lose), ("Y" to  GameResult.Draw), ("Z" to  GameResult.Win))
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