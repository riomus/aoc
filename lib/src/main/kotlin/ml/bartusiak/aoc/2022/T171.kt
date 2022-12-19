package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor

open class T171 : AOCTask {

    data class PlanePoint(val x: Long, val y: Long){
        operator fun plus(other: PlanePoint): PlanePoint{
            return PlanePoint(x+other.x, y+other.y)
        }
    }

    data class Stone(val points: Set<PlanePoint>){
        fun placeIn(start: PlanePoint): FallingStone{
            return FallingStone(start, this)
        }

        fun move(zero: PlanePoint): Stone {
            return Stone(points.map{it+zero}.toSet())
        }
    }

    data class FallingStone(val zero: PlanePoint, val stone: Stone){
        fun toStone(): Stone{
            return stone.move(zero)
        }
    }

    sealed interface StoneMove{

        fun move(stone: FallingStone): FallingStone
        data class Left(val units: Long):StoneMove{

            val base = PlanePoint(-units,0)
            override fun move(stone: FallingStone):FallingStone {
                return FallingStone(stone.zero+base, stone.stone)
            }
        }
        data class Right(val units: Long):StoneMove{

            val base = PlanePoint(units,0)
            override fun move(stone: FallingStone):FallingStone {
                return FallingStone(stone.zero+base, stone.stone)
            }
        }
        data class Down(val units: Long):StoneMove{

            val base = PlanePoint(0,-units)
            override fun move(stone: FallingStone):FallingStone {
                return FallingStone(stone.zero+base, stone.stone)
            }
        }

    }

    data class Env(val stopped: MutableSet<PlanePoint>){

        fun top(): Long = stopped.maxOfOrNull { it.y }?:0
        fun topFloor(): List<Long> {
            val ceil = top()
            val floorOnTop =stopped.filter { it.y==ceil }.map { it.x }
            return  floorOnTop
        }
        fun fallingStoneLocation(): PlanePoint{
            return PlanePoint(2, (stopped.maxOfOrNull { it.y }?:-1)+4)
        }

        fun move(stone: FallingStone, move: StoneMove): FallingStone{
            val afterMove = move.move(stone)
            return if(canMove(afterMove)){
                afterMove
            } else {
                stone
            }
        }
        fun canMove(stone: FallingStone): Boolean{
            val movedStone = stone.toStone()
            val onStopped = movedStone.points.any { stopped.contains(it) }
            val inRange = movedStone.points.all { it.x in 0..6 && it.y>=0 }

            return !onStopped && inRange
        }

        fun isSettled(stone: FallingStone): Boolean{
            return !canMove(FallingStone(stone.zero+PlanePoint(0, -1), stone.stone))
        }
    }

    data class Simulation(val env: Env, val fallingStone: FallingStone, val stoneIndex: Long, val jetIndex: Long)



    val stones = """
        ####

        .#.
        ###
        .#.

        ..#
        ..#
        ###

        #
        #
        #
        #

        ##
        ##
    """.trimIndent().split("\n\n").map{stoneLines ->
        val stonePoints = stoneLines.split("\n").reversed().flatMapIndexed{ y, line ->
            line.split("").filter { it.isNotEmpty() }.mapIndexedNotNull {  x, char ->
                if(char=="#"){
                    PlanePoint(x.toLong(),y.toLong())
                } else {
                    null
                }
            }
        }.toSet()
        Stone(stonePoints)
    }.toList()

    fun <T> List<T>.getFor(index: Long): T {
        return this[(index%this.size).toInt()]
    }

    val jetMap = mapOf(
        "<" to StoneMove.Left(1L),
        ">" to StoneMove.Right(1L)
    )
    open fun solve(file: String,stonesToWait : Long): Long{
        val jetPattern = data(file).first().split("").filter { it.isNotEmpty() }.map { rawJet ->
            jetMap[rawJet]!!
        }

        val gravity = StoneMove.Down(1)
        val stoneIndex = 0L
        val jetIndex = 0L
        val startEnv = Env(mutableSetOf())
        val startStone = stones.getFor(stoneIndex).placeIn(startEnv.fallingStoneLocation())
        val simulation = Simulation(startEnv, startStone, stoneIndex+1L, jetIndex)
        val finalSim = generateSequence(simulation){(currentEnv, currentStone, currentStoneIndex, currentJetIndex) ->

            println("$currentJetIndex,$currentStoneIndex, ${currentJetIndex%jetPattern.size}," +
                "${currentStoneIndex%stones.size},${currentEnv.top()},${currentEnv.topFloor().joinToString(";")}," +
                "${currentStone.zero.x};${currentStone.zero.y},${currentStone.stone.points.size}")
            val jetMove = jetPattern.getFor(currentJetIndex)
            val newJetIndex = currentJetIndex+1
            val afterJetMoveStone = currentEnv.move(currentStone, jetMove)
            val gravityMovedStone = currentEnv.move(afterJetMoveStone, gravity)
            if(gravityMovedStone==afterJetMoveStone){
                currentEnv.stopped.addAll(gravityMovedStone.toStone().points)
                val newStone = stones.getFor(currentStoneIndex).placeIn(currentEnv.fallingStoneLocation())
                Simulation(currentEnv, newStone,  currentStoneIndex+1, newJetIndex)
            } else {
                Simulation(currentEnv, gravityMovedStone,  currentStoneIndex, newJetIndex)
            }
        }.dropWhile { it.stoneIndex<=stonesToWait }.first()
        val (currentEnv, currentStone, currentStoneIndex, currentJetIndex) = finalSim
        println("$currentJetIndex,$currentStoneIndex, ${currentJetIndex%jetPattern.size}," +
            "${currentStoneIndex%stones.size},${currentEnv.top()},${currentEnv.topFloor().joinToString(";")}," +
            "${currentStone.zero.x};${currentStone.zero.y},${currentStone.stone.points.size}")
        return finalSim.env.top()+1L
    }


}