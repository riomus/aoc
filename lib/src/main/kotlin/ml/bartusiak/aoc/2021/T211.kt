package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import org.checkerframework.dataflow.qual.Deterministic

data class DeteministicDie(val nextPoint: Int, val rolls: Long){
    fun roll():Pair<DeteministicDie, Int>{
        return Pair(DeteministicDie(nextPoint%100+1, rolls+1), nextPoint)
    }
    fun roll(n: Int):Pair<DeteministicDie, Int>{
        return generateSequence(Pair(this, 0)){ (die, pt) ->
            val (newDie, newPt) = die.roll()
            Pair(newDie, newPt+pt)
        }.drop(n).first()
    }
}
data class Player(val position:Int,val points: Long,val pointsToWin: Int = 1000){
    val isWinner: Boolean = points>=pointsToWin
    fun move(n:Int): Player {
        val newPosition = (position+n-1)%10+1
       return  Player((position+n-1)%10+1,points + newPosition , pointsToWin)
    }
}
data class GameState(val player1: Player, val playr2: Player,val die: DeteministicDie){
    val hasWinner: Boolean = player1.isWinner || playr2.isWinner
    fun looser(): Player = if (hasWinner){if(player1.isWinner) playr2 else player1} else throw Exception("lol no winner")
}

open class T211 : AOCTask {

    companion object {
        fun solve(file: String = "T21.txt"): Long = T211().solve(file)
    }

    open fun solve(file: String): Long {
      val state = loadStartState(file)
      val finalState =   generateSequence(state, this::gameTurn).dropWhile { !it.hasWinner }.first()
        return finalState.looser().points*finalState.die.rolls
    }

    private fun gameTurn(state: GameState): GameState{
        val  (p1,p2,die) = state
        val (afterP1Die, p1Roll) = die.roll(3)
        val newP1 = p1.move(p1Roll)
        return if(newP1.isWinner){
            GameState(newP1, p2, afterP1Die)
        } else {
            val (afterP2Die, p2Roll) = afterP1Die.roll(3)
            val newP2 = p2.move(p2Roll)
            GameState(newP1, newP2, afterP2Die)
        }
    }


    fun loadStartState(file:String, pointsToWin: Int = 1000):GameState{
        val lines = data(file)
        val p1Start = lines.first().split(":").last().trim().toInt()
        val p2Start = lines.last().split(":").last().trim().toInt()
        return GameState(Player(p1Start, 0L, pointsToWin), Player(p2Start, 0L, pointsToWin) , DeteministicDie(1, 0L))
    }

}




