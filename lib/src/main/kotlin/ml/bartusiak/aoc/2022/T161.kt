package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

open class T161 : AOCTask {

//Valve AA has flow rate=0; tunnels lead to valves DD, II, BB

    data class Valve(val id: String, val flowRate: Long, val leadsTo: List<String>)


    data class Walking(val openValves: Set<String>, val releasedPreasure: Long, val currentValve: Valve, val path: List<String>)

    data class Simulation(val steps: Long, val walkings: Set<Walking>)
    open fun solve(file: String): Long{
        val valves = data(file).map{line ->
            val splited = line.split(" ", ",", ";", "=").filter{it.isNotEmpty()}
            Valve(splited[1], splited[5].toLong(),splited.drop(10))
        }

        val index = valves.associateBy { it.id }

        val startWalking = Walking(setOf(), 0, index["AA"]!!, listOf("AA"))
        val maxSteps = 30
        val walkings = generateSequence(Simulation(0, setOf(startWalking))){(step, walkings) ->
            val newWalkings = walkings.flatMap { walking ->
                val (open, released, current, path)= walking
                if(open.size==index.values.filter { it.flowRate>0 }.size){
                    listOf(walking)
                } else {
                    val goToWalkings = current.leadsTo.map { possibleNew ->
                        Walking(open, released, index[possibleNew]!!, path + possibleNew)
                    }
                    if( current.id !in open && current.flowRate!=0L){
                        goToWalkings+Walking(open+current.id, released+current.flowRate*(maxSteps-step-1), current, path + "O_${current.id}")
                    }else{
                        goToWalkings
                    }
                }
            }.toSet()
            val grouped = newWalkings.groupBy { (it.currentValve.id to it.openValves) }
            val filteredWalkings = grouped.entries.map { (current, walkings) ->
                walkings.maxBy { it.releasedPreasure }
            }.toSet()
            Simulation(step+1, filteredWalkings)
        }.dropWhile { it.steps<maxSteps-1 }.first()
        val maxWalking = walkings.walkings.maxBy{ it.releasedPreasure }
        println(maxWalking.path)
        return maxWalking.releasedPreasure
    }


}