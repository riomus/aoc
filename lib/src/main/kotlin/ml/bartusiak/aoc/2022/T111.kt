package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.RuntimeException
import kotlin.math.floor


open class T111 : AOCTask {

    sealed interface Test{

        fun test(value: Long): Boolean

        data class Divisable(val by: Long): Test{
            override fun test(value: Long): Boolean {
                return value%by==0L
            }
        }

    }
    sealed interface Operation{

        fun run(value: Long): Long

        sealed interface Operand{

            fun value(value: Long): Long
            data class Scalar(val value: Long): Operand{
                override fun value(value: Long): Long {
                    return this.value
                }
            }
            object Old: Operand{
                override fun value(value: Long): Long {
                    return value
                }
            }
        }

        data class Add(val left: Operand, val right: Operand): Operation{
            override fun run(value: Long): Long {
                return left.value(value)+ right.value(value)
            }
        }
        data class Subtract(val left: Operand, val right: Operand): Operation{
            override fun run(value: Long): Long {
                return left.value(value)- right.value(value)
            }
        }
        data class Multiply(val left: Operand, val right: Operand): Operation{
            override fun run(value: Long): Long {
                return left.value(value)* right.value(value)
            }
        }
        data class Divide(val left: Operand, val right: Operand): Operation{
            override fun run(value: Long): Long {
                return left.value(value)/right.value(value)
            }
        }
    }

    data class Monkey(
        val items: MutableList<Long>,
        val operation: Operation,
        val test: Test,
        val onTrue: Int,
        val onFalse: Int,
        var inspections: Long,
    )

    fun toOperand(repr: String): Operation.Operand{
        return try{
            Operation.Operand.Scalar(repr.toLong())
        } catch (e: NumberFormatException){
            Operation.Operand.Old
        }
    }
    fun toOperation(raw: String): Operation{
        val splited = raw.split(" ")
        val lefOperand = toOperand(splited[0])
        val rightOperand = toOperand(splited[2])
        return when(splited[1]){
            "+" -> Operation.Add(lefOperand, rightOperand)
            "-" -> Operation.Subtract(lefOperand, rightOperand)
            "*" -> Operation.Multiply(lefOperand, rightOperand)
            "/" -> Operation.Divide(lefOperand, rightOperand)
            else -> throw RuntimeException("Unknown operation $raw")
        }

    }

    fun toMonkey(lines: List<String>): Monkey{
        val startingItems = lines[1].split(":")[1].split(",").map { it.trim().toLong() }.toMutableList()
        val divisableBy = lines[3].split("by")[1].trim().toLong()
        val test = Test.Divisable(divisableBy)
        val onTrue = lines[4].split("monkey")[1].trim().toInt()
        val onFalse = lines[5].split("monkey")[1].trim().toInt()
        val rawOperation = lines[2].split("=")[1].trim()
        val operation = toOperation(rawOperation)
        return Monkey(
            startingItems,
            operation,
            test,
            onTrue,
            onFalse,
            0L
        )
    }
     fun solve(file: String, rounds: Int = 20, worryMapper: (Long) -> Long = { l -> floor(l/3.0).toLong() }): Long {
        val monkeys = data(file).windowed(7, step = 7, true).map(::toMonkey)
         val testsRing = monkeys.map { it.test as Test.Divisable }.map { it.by }.reduce{l1, l2 -> l1*l2}
         repeat(rounds){
             monkeys.forEach{monkey ->
                 monkey.inspections += monkey.items.size
                 monkey.items.forEach{ item ->
                     val newWorry = worryMapper(monkey.operation.run(item)) % testsRing
                     if(monkey.test.test(newWorry)){
                         monkeys[monkey.onTrue].items.add(newWorry)
                     } else {
                         monkeys[monkey.onFalse].items.add(newWorry)
                     }
                 }
                 monkey.items.clear()
             }
         }
        return monkeys
            .sortedByDescending { it.inspections }.take(2).map { it.inspections }.reduce{i1, i2 -> i1*i2}
    }

}