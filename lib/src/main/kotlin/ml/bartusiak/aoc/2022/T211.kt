package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor
import java.lang.Math.min
import java.lang.RuntimeException
import java.util.Collections
import kotlin.math.abs
import kotlin.math.sign

open class T211 : AOCTask {


    sealed interface Monkey{

        val id: String

        fun getValue(data: Map<String, Long>): Long
        fun getOperandsMonkeys(): Set<String>
        data class LoneMonkey(override val id: String, val value: Long):Monkey{
            override fun getOperandsMonkeys(): Set<String> {
                return emptySet()
            }
            override fun getValue(data: Map<String, Long>): Long {
                return value
            }
        }

        data class ComputingMonkey(override val id: String, val operation: Operation):Monkey{
            override fun getOperandsMonkeys(): Set<String> {
                return operation.getOperandsMonkeys()
            }
            override fun getValue(data: Map<String, Long>): Long {
                return operation.getValue(data)
            }
        }
    }

    sealed interface Operation{
        val left: Operand
        val right: Operand


        fun getOperandsMonkeys(): Set<String>
        fun getValue(data: Map<String, Long>): Long

        fun inverse(data: Map<String, Long>, value: Long): Pair<Operand, Long>

        data class Add(override val left: Operand,override  val right: Operand): Operation{
            override fun getOperandsMonkeys(): Set<String> {
                return left.getOperandMonkeys() + right.getOperandMonkeys()
            }

            override fun getValue(data: Map<String, Long>): Long {
                return left.getValue(data)+ right.getValue(data)
            }

            override fun inverse(data: Map<String, Long>, value: Long): Pair<Operand, Long>{
               return if(left.hasValue(data)){
                    right to value-left.getValue(data)
                } else {
                    left to value - right.getValue(data)
                }
            }
        }
        data class Minus(override val left: Operand,override  val right: Operand): Operation{
            override fun getOperandsMonkeys(): Set<String> {
                return left.getOperandMonkeys() + right.getOperandMonkeys()
            }

            override fun getValue(data: Map<String, Long>): Long {
                return left.getValue(data)- right.getValue(data)
            }

            override fun inverse(data: Map<String, Long>, value: Long): Pair<Operand, Long>{
                return if(left.hasValue(data)){
                    right to left.getValue(data)-value
                } else {
                    left to value + right.getValue(data)
                }
            }
        }
        data class Div(override val left: Operand,override  val right: Operand): Operation{
            override fun getOperandsMonkeys(): Set<String> {
                return left.getOperandMonkeys() + right.getOperandMonkeys()
            }

            override fun getValue(data: Map<String, Long>): Long {
                return left.getValue(data)/ right.getValue(data)
            }
            override fun inverse(data: Map<String, Long>, value: Long): Pair<Operand, Long>{
                return if(left.hasValue(data)){
                    right to left.getValue(data)/value
                } else {
                    left to value * right.getValue(data)
                }
            }
        }
        data class Mul(override val left: Operand,override  val right: Operand): Operation{
            override fun getValue(data: Map<String, Long>): Long {
                return left.getValue(data)* right.getValue(data)
            }
            override fun getOperandsMonkeys(): Set<String> {
                return left.getOperandMonkeys() + right.getOperandMonkeys()
            }
            override fun inverse(data: Map<String, Long>, value: Long): Pair<Operand, Long>{
                return if(left.hasValue(data)){
                    right to value/left.getValue(data)
                } else {
                    left to value / right.getValue(data)
                }
            }
        }

        sealed interface Operand{

            fun getOperandMonkeys(): Set<String>

            fun getValue(data: Map<String, Long>):Long

            fun hasValue(data: Map<String, Long>):Boolean

            data class Scalar(val value: Long): Operand{
                override fun getValue(data: Map<String, Long>): Long {
                    return value
                }

                override fun hasValue(data: Map<String, Long>): Boolean {
                    return true
                }

                override fun getOperandMonkeys(): Set<String> {
                    return emptySet()
                }
            }

            data class MonkeyValue(val monkey: String):Operand{

                override fun hasValue(data: Map<String, Long>): Boolean {
                    return data.containsKey(monkey)
                }
                override fun getValue(data: Map<String, Long>):Long {
                    return data[monkey]!!
                }

                override fun getOperandMonkeys(): Set<String> {
                    return setOf(monkey)
                }
            }

        }
    }

    fun String.toOperand(): Operation.Operand{
        return try{
            Operation.Operand.Scalar(this.toLong())
        } catch(e: NumberFormatException) {
            Operation.Operand.MonkeyValue(this)
        }
    }

    val operationsMap: Map<String, (Operation.Operand, Operation.Operand) -> Operation> = mapOf(
        "+" to {a: Operation.Operand, b: Operation.Operand -> Operation.Add(a, b)},
        "-" to {a: Operation.Operand, b: Operation.Operand -> Operation.Minus(a, b)},
        "/" to {a: Operation.Operand, b: Operation.Operand -> Operation.Div(a, b)},
        "*" to {a: Operation.Operand, b: Operation.Operand -> Operation.Mul(a, b)}
    )

    open fun solve(file: String): Long{

        val monkeys = data(file).map { line ->
            val splited = line.split(":")
            val id = splited[0]
            val equation = splited[1].split(" ").filter { it.isNotEmpty() }
            if(equation.size>2){
                val operation = operationsMap[equation[1]]!!(equation[0].toOperand(), equation[2].toOperand())
                Monkey.ComputingMonkey(id, operation)
            }else {
                Monkey.LoneMonkey(id, equation[0].toLong())
            }

        }.associateBy{it.id}

        val computeOrder = ArrayDeque<String>()
        computeOrder.addLast("root")
        val toFind = ArrayDeque<String>()
        val root = monkeys["root"]!!
        toFind.addAll(root.getOperandsMonkeys())

        while(toFind.isNotEmpty()){
            val currentFind = toFind.removeFirst()
            val currentMonkey = monkeys[currentFind]!!
            computeOrder.addLast(currentFind)
            toFind.addAll(currentMonkey.getOperandsMonkeys())
        }
        val data = mutableMapOf<String, Long>()
        while(computeOrder.isNotEmpty()){
            val toCompute = computeOrder.removeLast()
            val monkey = monkeys[toCompute]!!
            val value = monkey.getValue(data)
            data[toCompute]=value
        }
        return data["root"]!!
        }

    }

