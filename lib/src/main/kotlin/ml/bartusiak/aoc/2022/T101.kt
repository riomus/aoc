package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.max
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction2


open class T101 : AOCTask {

    sealed interface Command{

        fun run(registers: Registers): List<Triple<Registers,Registers,Registers>>

        object Noop: Command{
            override fun  run(registers: Registers): List<Triple<Registers,Registers,Registers>> {
                return listOf(Triple(registers,registers,registers))
            }
        }
        data class AddX(val amount: Int): Command{
            override fun  run(registers: Registers): List<Triple<Registers,Registers,Registers>> {
                return listOf(Triple(registers,registers,registers), Triple(registers, registers, registers.copy(x = registers.x + amount)))
            }
        }
    }

    val toCommandCreator = mapOf(
        ("noop" to {line: List<String> ->
            Command.Noop
        }),
        ("addx" to {line: List<String> ->
            Command.AddX(line[1].toInt())
        })
    )

    data class Registers(val x: Int)

    open fun toHistory(file:String): List<Triple<Registers, Registers, Registers>> {
        val commands = data(file).map{it.split(" ")}.map { line ->
            toCommandCreator[line[0]]!!(line)
        }
        val start = Registers(1)
        val registersHistory = commands.fold(mutableListOf(Triple(start, start, start))){ acc, command ->
            val lastRegister = acc.last()
            val newRegisters = command.run(lastRegister.third)
            acc.addAll(newRegisters)
            acc
        }
        return registersHistory
    }

    open fun solve(file: String = "2022/T91.txt"): Int {

        //20th, 60th, 100th, 140th, 180th
        val toPeak = setOf(20,60,100,140,180,220)
        val peaked=  toHistory(file).mapIndexedNotNull{ ind, reg ->
            if(ind in toPeak){
                (ind to reg.second.x)
            } else {
                null
            }
        }
           return peaked.sumOf { it.first * it.second  }
    }

}