package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor
import java.lang.Math.min
import java.lang.RuntimeException
import java.util.Collections
import kotlin.math.abs
import kotlin.math.sign

open class T201 : AOCTask {


    data class Number(val originalIndex: Int, val value: Long)

    data class CircularList(val data: MutableList<Number>){

        fun at(index: Int): Number{
            return data[index%data.size]
        }

        fun move(from: Int, moves: Long){
            if(moves!=0L){
                val el = data.removeAt(from%data.size)
                val newIndex = (from+moves).mod(data.size)
                data.add(newIndex, el)
            }
        }

        fun afterZero(index: Int): Number {
            val values = data.map{it.value}
            val zeroIndex = values.indexOf(0)
            return data[(zeroIndex+index)%data.size]!!
        }
    }

    open fun solve(file: String, mixes: Int = 1, key: Long = 1L): Long{
        val numbers = data(file).mapIndexed{idx, it ->
            Number(idx, it.toLong()*key)
        }.toMutableList()

        val list = CircularList(numbers)
        repeat(mixes){
            (0 until numbers.size).forEach{ idx ->
                val elem = list.data.find { it.originalIndex==idx }!!
                val from = list.data.indexOf(elem)
                list.move(from, elem.value)
            }
        }


        val n1=list.afterZero(1000).value
        val n2=list.afterZero(2000).value
        val n3=list.afterZero(3000).value
        return n1+n2+n3
        }

    }

