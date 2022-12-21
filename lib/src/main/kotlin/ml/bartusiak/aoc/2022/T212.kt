package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor
import java.lang.Math.min
import java.lang.RuntimeException
import java.util.Collections
import kotlin.math.abs
import kotlin.math.sign

open class T212 : T211() {

    override fun solve(file: String): Long{

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

        }.associateBy{it.id}.toMutableMap()
        monkeys.remove("humn")
        val computeOrder = ArrayDeque<String>()
        computeOrder.addLast("root")
        val toFind = ArrayDeque<String>()
        val root = monkeys["root"]!!
        toFind.addAll(root.getOperandsMonkeys())

        while(toFind.isNotEmpty()){
            val currentFind = toFind.removeFirst()
            try {
                val currentMonkey = monkeys[currentFind]!!
                computeOrder.addLast(currentFind)
                toFind.addAll(currentMonkey.getOperandsMonkeys())
            } catch (exception: Exception){

            }
        }
        val data = mutableMapOf<String, Long>()
        while(computeOrder.isNotEmpty()){
            val toCompute = computeOrder.removeLast()
            val monkey = monkeys[toCompute]!!
            try {
                val value = monkey.getValue(data)
                data[toCompute] = value
            } catch (exception: Exception){

            }
        }
        val startOp = (root as Monkey.ComputingMonkey).operation
        var left = startOp.left
        var right = startOp.right
        val humanOperands = setOf("humn")

        while(left.getOperandMonkeys() != humanOperands && right.getOperandMonkeys()!= humanOperands){
            if(left.getOperandMonkeys().all { it in data }){
                val rightMonkey = monkeys[right.getOperandMonkeys().first()] as Monkey.ComputingMonkey
                val (newRight, newValue ) = rightMonkey.operation.inverse(data, left.getValue(data))
                right = newRight
                left = Operation.Operand.Scalar(newValue)
            } else {
                val leftMonkey = monkeys[left.getOperandMonkeys().first()] as Monkey.ComputingMonkey
                val (newLeft, newValue ) = leftMonkey.operation.inverse(data, right.getValue(data))
                left = newLeft
                right = Operation.Operand.Scalar(newValue)
            }
        }

        return if(left is Operation.Operand.Scalar){
            left.value
        } else {
            ( right as Operation.Operand.Scalar).value
        }

        }

    }

