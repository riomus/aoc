package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

open class T162 : AOCTask {

//Valve AA has flow rate=0; tunnels lead to valves DD, II, BB

    data class Valve(val id: String, val flowRate: Long, val leadsTo: List<String>)


    data class Walking(val openValves: Set<String>, val releasedPreasure: Long, val currentValve: Valve, val currentElephant: Valve, val path: List<String>)

    data class PartialMove(val flowUpdate: Long, val valve: Valve, val strRepr: String, val toOpen: Set<String>)
    data class Simulation(val steps: Long, val walkings: Set<Walking>)
    open fun solve(file: String): Long{
        val valves = data(file).map{line ->
            val splited = line.split(" ", ",", ";", "=").filter{it.isNotEmpty()}
            Valve(splited[1], splited[5].toLong(),splited.drop(10))
        }

        val index = valves.associateBy { it.id }

        val startWalking = Walking(setOf(), 0, index["AA"]!!, index["AA"]!!, listOf("AA"))
        val maxSteps = 26
        val walkings = generateSequence(Simulation(0, setOf(startWalking))){(step, walkings) ->
            val newWalkings = walkings.flatMap { walking ->
                val (open, released, current, elephantCurrent, path)= walking
                if(open.size==index.values.filter { it.flowRate>0 }.size){
                    listOf(walking)
                } else {
                    val possibleOpenCurrent  = if( current.id !in open && current.flowRate!=0L){
                        listOf(PartialMove(current.flowRate*(maxSteps-step-1) , current , "O_${current.id}", setOf(current.id)))
                    }else{
                        emptyList()
                    }
                    val possibleOpenElephant  = if( elephantCurrent.id !in open && elephantCurrent.flowRate!=0L){
                        listOf(PartialMove(elephantCurrent.flowRate*(maxSteps-step-1) , elephantCurrent , "O_${elephantCurrent.id}", setOf(elephantCurrent.id)))
                    }else{
                        emptyList()
                    }
                    val possibleCurrent = current.leadsTo.map{ next -> PartialMove(0L, index[next]!!, next, emptySet())} + possibleOpenCurrent
                    val possibleElephant = elephantCurrent.leadsTo.map{ next -> PartialMove(0L, index[next]!!, next, emptySet())} + possibleOpenElephant
                    possibleCurrent.flatMap { (increaseCurrent, nextCurrent, nextCurrentStr, toOpenCurrent) ->
                        possibleElephant.filter { it.toOpen!=toOpenCurrent || it.toOpen.isEmpty() }.map{(increaseElephant, nextElephant, nextElephantStr, toOpenElephant) ->
                            Walking(open+toOpenElephant+toOpenCurrent, released+increaseCurrent+increaseElephant, nextCurrent, nextElephant, path + "($nextCurrentStr, $nextElephantStr)")
                        }
                    }
                }
            }
            val filteredWalkings = newWalkings.fold(mutableMapOf<Pair<Set<String>, Set<String>>, Walking>()){acc, walking ->
                val key = ((setOf(walking.currentValve.id, walking.currentElephant.id)) to walking.openValves)
                acc[key]?.let {
                    if(it.releasedPreasure<walking.releasedPreasure){
                        acc[key]=walking
                    }
                }?:run{
                    acc[key]=walking
                }
                acc
            }.values.groupBy { it.releasedPreasure }.entries.sortedByDescending { it.key }.take(100).flatMap { it.value }.toSet()
            println("$step ${filteredWalkings.size}")
            Simulation(step+1, filteredWalkings)
        }.dropWhile { it.steps<maxSteps-1 }.first()
        val maxWalking = walkings.walkings.maxBy{ it.releasedPreasure }
        println(maxWalking.path)
        return maxWalking.releasedPreasure
    }


}