package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.max
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction2


open class T91 : AOCTask {

    sealed interface Move{

        val amount: Int
        val unitMove: Cords
        fun move(head: Cords, tail: Cords): Triple<Cords, Cords, MutableSet<Cords>>{
            return (0 until amount).fold(Triple(head, tail, mutableSetOf())){ (head, tail, history), _ ->
                val newHead = moveUnit(head)
                val newTail = moveTail(newHead, tail)
                history.add(newTail)
                Triple(newHead, newTail, history)
            }
        }

        fun moveUnit(cord: Cords): Cords{
            return cord + unitMove
        }

        fun shouldMove(head: Cords, tail: Cords): Boolean{
            val(x, y) = (head-tail).abs()
            return x>1 || y>1
        }
        fun moveTail(head: Cords, tail: Cords): Cords {
            return if(shouldMove(head, tail)){
                val (mx, my) = (head-tail)
                val (dx, dy) = (head-tail).abs()
                val move = if(dx>0 && dy>0){
                   Cords(mx.followDiagonal(), my.followDiagonal())

                } else {
                    Cords(mx.follow(), my.follow())
                }
                tail+move
            } else {
                tail
            }
        }

        private fun Int.followDiagonal(): Int{
            val newValue = this.decrement()
            return if(newValue!=0){
                newValue
            } else{
                this
            }
        }
        private fun Int.follow(): Int{
            return if(this!=0){
                this.decrement()
            } else{
                this
            }
        }
        private fun Int.decrement(): Int{
            return if(this<0){
                this+1
            } else if(this>0){
                this-1
            } else {
                this
            }
        }
        data class Up(override val amount: Int): Move{
            override val unitMove = Cords(0, 1)
        }
        data class Down(override val amount: Int): Move{
            override val unitMove = Cords(0, -1)
        }
        data class Left(override val amount: Int): Move{
            override val unitMove = Cords(-1, 0)
        }
        data class Right(override val amount: Int): Move{
            override val unitMove = Cords(1, 0)
        }
    }
    val movesCreators = mapOf(
        ("D" to {a: Int -> Move.Down(a)}),
        ("U" to {a: Int -> Move.Up(a)}),
        ("L" to {a: Int -> Move.Left(a)}),
        ("R" to {a: Int -> Move.Right(a)})
    )

    data class Cords(val x: Int, val y: Int){
        operator fun minus(other: Cords): Cords{
            return Cords(this.x-other.x, this.y-other.y)
        }
        operator fun plus(other: Cords): Cords{
            return Cords(this.x+other.x, this.y+other.y)
        }
        fun abs(): Cords{
            return Cords(x.absoluteValue, y.absoluteValue)
        }
        fun inv(): Cords{
            return Cords(-1*x, -1*y)
        }
    }
    data class State(val visited: MutableSet<Cords>, val currentHead: Cords, val currentTail: Cords)

    open fun solve(file: String = "2022/T91.txt"): Int {
        val moves = data(file).map{line ->
            val splited = line.split(" ")
            val direction = splited[0]
            val amount = splited[1].toInt()
            movesCreators[direction]!!(amount)
        }
        val tailStart =  Cords(0, 0)
        val startState = State(mutableSetOf(), Cords(0, 0), tailStart)
        val (visited, endHead, endTail ) = moves.fold(startState){ state, move ->
            val (newHead, newTail, tailHistory) = move.move(state.currentHead, state.currentTail)
            state.visited.addAll(tailHistory)
            State(state.visited, newHead, newTail)
        }
        return visited.size
    }

}