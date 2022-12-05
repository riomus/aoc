package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil



open class T51 : AOCTask {

    data class Move(val from: Int, val to: Int, val amount: Int)
    data class CraneStacks(val stacks: List<ArrayDeque<Char>>, val moves: List<Move>)

    open fun moveCrates(acc: List<ArrayDeque<Char>>, move: Move): List<ArrayDeque<Char>> {
        repeat(move.amount){
            acc[move.to-1].add(0, acc[move.from-1].removeFirst())
        }
        return acc
    }
    fun solve(file: String = "2022/T51.txt"): String {
        val rawStacks = dataNoTrim(file).takeWhile { it.isNotEmpty() }.filter { it.isNotEmpty() }.dropLast(1)
        val rawMoves = dataNoTrim(file).dropWhile { it.isNotEmpty() }.filter { it.isNotEmpty() }
        val moves = rawMoves.map { line ->
            val splited = line.split(" ")
            Move(splited[3].toInt(), splited[5].toInt(),  splited[1].toInt())
        }
        val stacks = List(ceil(rawStacks.first().length/4.0).toInt()){ArrayDeque<Char>()}
        rawStacks.map{line ->
            line.windowed(4, step = 4, partialWindows = true).mapIndexed{ idx, str ->
                if(str.startsWith("[")){
                    stacks[idx].add(str[1])
                }
            }
        }


        val finalState = moves.fold(stacks) { acc, move ->
            moveCrates(acc, move)
        }
        return finalState.map { it.first() }.joinToString("")
    }

}