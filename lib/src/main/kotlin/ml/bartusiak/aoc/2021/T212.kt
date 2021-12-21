package ml.bartusiak.aoc.`2021`

import kotlin.math.max

object DiracDie{
    fun roll():List<Int>{
        return listOf(1,2,3)
    }
    val roll3 = roll(3).toList()
    fun roll(n: Int):Sequence<Int>{
        return generateSequence(listOf(0).asSequence()){ dies ->
            dies.asSequence().flatMap { pt ->
                val newDies = roll()
                newDies.asSequence().map{ newPoints->
                    newPoints+pt
                }
            }
        }.drop(n).first()
    }
}
data class DiracGameState(val player1: Player, val playr2: Player){
    val hasWinner: Boolean = player1.isWinner || playr2.isWinner
    fun winnerPair(): Pair<Long, Long> = if(player1.isWinner) Pair(1L,0) else Pair(0,1L)
}
open class T212 : T211() {

    companion object {
        fun solve(file: String = "T21.txt"): Long = T212().solve(file)
    }

    operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = Pair(this.first+other.first, this.second+other.second)
    override fun solve(file: String): Long {
        val state = loadDiracState(file, 21)
        val (points, _) = playState(listOf(state), mutableMapOf())
        return max(points.first, points.second)
    }


    data class DynamicStep(val points:  Pair<Long, Long>,val computed: MutableMap<DiracGameState, Pair<Long,Long>> )
    fun List<DiracGameState>.points(): Pair<Long, Long> = this.fold(Pair(0L,0L)){acc, it -> it.winnerPair()+acc}

     tailrec fun playState(state: List<DiracGameState>, computed: MutableMap<DiracGameState, Pair<Long,Long>>): DynamicStep{
        val (winning, toContinue) = state.partition { it.hasWinner }
        val winningPoints = winning.points()
        val (newPoints, newComputed) = toContinue.fold(DynamicStep(Pair(0L, 0L), computed)) {(points, accComputed), it ->
            accComputed[it]?.let{ DynamicStep(it+points, accComputed)}?:run {
                val (newPoints, newComputed) = playState(singleDiracStep(it), accComputed)
                newComputed[it]=newPoints
                DynamicStep(newPoints+points, newComputed)
            }
        }
        return DynamicStep(winningPoints+newPoints, newComputed)
    }

    private fun singleDiracStep(state: DiracGameState): List<DiracGameState>{
        return if(!state.hasWinner) {
            val (p1, p2) = state
            val p1Rolls = DiracDie.roll3
            val result: List<DiracGameState> = p1Rolls.flatMap { p1Roll ->
                val newP1 = p1.move(p1Roll)
                if (newP1.isWinner) {
                   listOf(DiracGameState(newP1, p2))
                } else {
                    val p2Rolls = DiracDie.roll3
                    p2Rolls.map { p2Roll ->
                        val newP2 = p2.move(p2Roll)
                        DiracGameState(newP1, newP2)
                    }
                }
            }
            result
        } else {
            listOf(state)
        }

    }


     fun loadDiracState(file:String, pointsToWin: Int = 1000):DiracGameState{
        val lines = data(file)
        val p1Start = lines.first().split(":").last().trim().toInt()
        val p2Start = lines.last().split(":").last().trim().toInt()
        return DiracGameState(Player(p1Start, 0L, pointsToWin), Player(p2Start, 0L, pointsToWin))
    }

}




