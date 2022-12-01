package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


open class T251 : AOCTask {

    companion object {
        fun solve(file: String = "2021/T251.txt"): Long = T251().solve(file)
    }


    open fun solve(file: String): Long {
        val startMap = loadState(file)
        val result = generateSequence(startMap, this::moveStep)
            .mapIndexed{idx, state -> Pair(idx, state)}
            .windowed(2)
            .dropWhile { it.first().second!=it.last().second }
            .first().last().first
        return result.toLong()
    }

    fun moveStep(state: GameState): GameState{
        val result = state.move()
        return result
    }

    enum class CucumberOrientation{East, South}

    data class Cucumber(val point: Pair<Long, Long>, val orientation: CucumberOrientation){
        fun nextPosition(maxX: Long, maxY: Long): Cucumber {
            return when(orientation){
                CucumberOrientation.East -> Cucumber(Pair((point.first+1)%maxX, point.second), orientation)
                CucumberOrientation.South  -> Cucumber(Pair(point.first, (point.second+1)%(maxY)), orientation)
            }
        }
    }

    data class GameState(val herd: MutableList<Cucumber>, val maxX: Long, val maxY: Long){
        fun move(): GameState{
            val after = mutableListOf<Cucumber>()
            val positions = mutableSetOf<Pair<Long, Long>>()
            positions.addAll(herd.map{it.point})
            val toMove = herd.asSequence()
            val (eastFacing, southFacing) = toMove.partition{ it.orientation==CucumberOrientation.East }
            fun moveHerd(herd: List<Cucumber>){
                herd.forEach { cucumber ->
                    val nextCucumber = cucumber.nextPosition(maxX, maxY)
                    if(!positions.contains(nextCucumber.point)){
                        after.add(nextCucumber)
                    } else {
                       after.add(cucumber)
                    }
                }
            }
            moveHerd(eastFacing)
            positions.removeAll(eastFacing.map { it.point })
            positions.addAll(after.map { it.point })
            moveHerd(southFacing)
            return GameState(after, maxX, maxY)
        }

        override fun toString(): String {
            return (0 until maxY).joinToString("\n") { y ->
                (0 until maxX).joinToString("") { x ->
                    if(herd.contains(Cucumber(Pair(x, y), CucumberOrientation.South))) {
                        "v"
                    } else if(herd.contains(Cucumber(Pair(x, y), CucumberOrientation.East))){
                        ">"
                    } else {
                        "."
                    }
                }
            }
        }
    }

    fun loadState(file:String): GameState {
        val lines = data(file).map { it.toCharArray().map{it.toString()} }
        val maxY = lines.size
        val maxX = lines.first().size
        val cucumbers = lines.flatMapIndexed{y, line ->
            line.flatMapIndexed{ x, point ->
                if(point=="."){
                    emptyList()
                } else {
                    val orientation = if(point==">") CucumberOrientation.East else CucumberOrientation.South
                    listOf(Cucumber(Pair(x.toLong(), y.toLong()), orientation))
                }
            }
        }.toMutableList()
        return GameState(cucumbers, maxX.toLong(), maxY.toLong())
    }

}



