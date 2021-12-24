package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


typealias Instructions = List<List<T241.Instruction>>
typealias CacheKey = Pair<Int, List<T241.Instruction>>
open class T241 : AOCTask {

    companion object {
        fun solve(file: String = "T24.txt"): Long = T241().solve(file)
    }

    open fun solve(file: String): Long{
        val instructions: Instructions = loadInstructions(file)
        val solution = findSolution(instructions, (1 .. 9).reversed().asSequence())
        return solution
    }

    fun findSolution(instructions: Instructions, digits: Sequence<Int>): Long {
        return findNum(instructions, 0, mutableMapOf(), digits)!!.toString().reversed().toLong()
    }

    fun findNum(instructions: Instructions, z: Int, cache: MutableMap<CacheKey, Long?>, digits: Sequence<Int>): Long?{
        val currentBlock = instructions.first()
        val cacheKey = Pair(z, currentBlock)
        return if(cache.contains(cacheKey)){
            cache[cacheKey]
        } else {
           val number: Long? =  digits.flatMap { digit ->
                val result = currentBlock.run(Registries(0,0,0,z = z), digit)
                if(instructions.size==1){
                    if(result.z==0){
                        listOf(digit.toLong())
                    } else {
                        cache[Pair(z, currentBlock)]=null
                        listOf()
                    }
                } else {
                    val nested= findNum(instructions.drop(1), result.z, cache, digits)
                    if(nested!=null){
                        val nextDigit = nested*10+digit
                        listOf(nextDigit)
                    } else {
                        cache[Pair(z, currentBlock)]=null
                        listOf()
                    }
                }
            }.firstOrNull()
            cache[Pair(z, currentBlock)]=number
            number
        }
    }

    fun loadInstructions(file: String): Instructions {
        return data(file).fold(mutableListOf<MutableList<Instruction>>()){ acc, line ->
            val splited = line.split(" ")
            when(splited.first()){
                "inp" -> {
                    acc.add(mutableListOf(Input(splited.last().registry())))
                }
                "add" -> {
                    acc.last().add(Add(splited[1]!!.registry(), splited.last().operationInput()))
                }
                "mul" -> {
                    acc.last().add(Mul(splited[1]!!.registry(), splited.last().operationInput()))
                }
                "div" -> {
                    acc.last().add(Div(splited[1]!!.registry(), splited.last().operationInput()))
                }
                "mod" -> {
                    acc.last().add(Mod(splited[1]!!.registry(), splited.last().operationInput()))
                }
                "eql" -> {
                    acc.last().add(Eql(splited[1]!!.registry(), splited.last().operationInput()))
                }
                else -> throw Exception("Unknown op")
            }
            acc
        }
    }

    data class Registries(var w: Int, var x: Int, var y: Int, var z: Int){
        fun get(reg: Registry): Int{
            return when(reg){
                Registry.W -> w
                Registry.X -> x
                Registry.Y -> y
                Registry.Z -> z
            }
        }
        fun set(reg: Registry, value: Int): Registries{
            when(reg){
                Registry.W -> w=value
                Registry.X -> x=value
                Registry.Y -> y=value
                Registry.Z -> z=value
            }
            return this
        }
    }

    enum class Registry{W, X, Y, Z}
    fun String.registry(): Registry{
        return Registry.valueOf(this.uppercase())
    }
    fun String.operationInput(): OperationInput{
        return this.toIntOrNull()?.let{Literal(it)}?:RegistryValue(this.registry())
    }

    sealed interface Instruction{
        fun run(regs: Registries, input: Int): Registries
    }

    fun List<Instruction>.run(regs: Registries, input: Int): Registries {
        return this.fold(regs){acc, inst ->
            inst.run(acc, input)
        }
    }

    sealed interface OperationInput{
        fun value(registries: Registries): Int
    }
    data class Literal(val literalValue: Int): OperationInput{
        override  fun value(registries: Registries): Int = literalValue
    }
    data class RegistryValue(val reg: Registry): OperationInput{
        override  fun value(registries: Registries): Int = registries.get(reg)
    }

    data class Input(val registry: Registry): Instruction{
        override fun run(regs: Registries, input: Int): Registries{
            return regs.set(registry, input)
        }
    }
    data class Add(val a: Registry, val b: OperationInput): Instruction{
        override fun run(regs: Registries, input: Int): Registries{
            return regs.set(a, regs.get(a)+b.value(regs))
        }
    }
    data class Mul(val a: Registry, val b: OperationInput): Instruction{
        override fun run(regs: Registries, input: Int): Registries{
            return regs.set(a, regs.get(a)*b.value(regs))
        }
    }
    data class Div(val a: Registry, val b: OperationInput): Instruction{
        override fun run(regs: Registries, input: Int): Registries{
            return regs.set(a, regs.get(a)/b.value(regs))
        }
    }
    data class Mod(val a: Registry, val b: OperationInput): Instruction{
        override fun run(regs: Registries, input: Int): Registries{
            return regs.set(a, regs.get(a)%b.value(regs))
        }
    }
    data class Eql(val a: Registry, val b: OperationInput): Instruction{
        override fun run(regs: Registries, input: Int): Registries{
            return regs.set(a, if(regs.get(a)==b.value(regs)) 1 else 0)
        }
    }
}



