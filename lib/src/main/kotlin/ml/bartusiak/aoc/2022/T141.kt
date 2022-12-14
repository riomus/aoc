package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.max
import java.lang.Math.min
import java.lang.Math.sqrt


open class T141 : AOCTask {

    data class RockFormations(val formations: Set<RockFormation>){
        fun isFreePoint(point: PlanePoint): Boolean{
            return formations.all { it.isFreePoint(point) }
        }
    }


    data class PlanePoint(val x: Long, val y: Long){
        operator fun plus(other: PlanePoint): PlanePoint{
            return PlanePoint(x + other.x, y + other.y)
        }

        fun Long.sqr(): Long{
            return this*this
        }
    }

    sealed interface RockLine{
        fun isFreePoint(point: PlanePoint): Boolean
        data class StandardRockLine(val start: PlanePoint, val end: PlanePoint):RockLine{

            val isHorizontal = start.y == end.y
            val maxX = max(start.x, end.x)
            val minX = min(start.x, end.x)
            val maxY = max(start.y, end.y)
            val minY = min(start.y, end.y)
            override fun isFreePoint(point: PlanePoint): Boolean{
                return if(isHorizontal){
                    point.y !=start.y || point.x>maxX || point.x<minX
                }else{

                    point.x !=start.x || point.y>maxY || point.y<minY
                }
            }
        }
        data class Floor(val y: Long):RockLine{
            override fun isFreePoint(point: PlanePoint): Boolean{
                return point.y<y
            }
        }
    }
    data class RockFormation(val rocks: Set<RockLine>){
        fun isFreePoint(point: PlanePoint): Boolean{
            return rocks.all { it.isFreePoint(point) }
        }
    }


    data class SimulationEnvironment(val formations: RockFormations, val sandSource: PlanePoint){

        val ifFreeCache = HashMap<PlanePoint, Boolean>()
        fun isFreePoint(point: PlanePoint): Boolean{
            return ifFreeCache.get(point) ?: run{
                val isFree =  formations.isFreePoint(point)
                ifFreeCache[point] = isFree
                isFree
            }
        }
        fun newSand(): PlanePoint{
            return sandSource
        }
    }
    data class Simulation(val env: SimulationEnvironment, val currentSand: PlanePoint, val settledSand: MutableSet<PlanePoint>){
        val moves = listOf(
            PlanePoint(0, 1),
            PlanePoint(-1, 1),
            PlanePoint(1, 1)
        )
        fun isSettled(point: PlanePoint): Boolean{
            return moves.map{it+point}.firstOrNull(::isFreePoint) == null
        }

        fun isFreePoint(point: PlanePoint): Boolean{
            return !settledSand.contains(point) && env.isFreePoint(point)
        }
        fun move(point: PlanePoint): PlanePoint{
            return moves.map{it+point}.first(::isFreePoint)
        }
    }

    fun getSimulation(file: String): Simulation{

        val rockFormations = data(file).map { line ->
            val lines = line.split("->").map {
                val data = it.trim().split(",").map { it.toLong() }
                PlanePoint(data[0], data[1])
            }.windowed(2, 1).map { data ->
                val start = data[0]
                val end = data[1]
                RockLine.StandardRockLine(start, end)
            }
            RockFormation(lines.toSet())
        }
        val formation = RockFormations(rockFormations.toSet())
        val startPoint = PlanePoint(500, 0)


        val env = SimulationEnvironment(formation, startPoint)
        return Simulation(env, startPoint, HashSet())
    }
    open fun solve(file: String): Int {
        val sim = getSimulation(file)
        val maxY = sim.env.formations.formations.flatMap { it.rocks }.map{it as RockLine.StandardRockLine}.flatMap { listOf(it.start.y, it.end.y) }.max()
        val finalSim= generateSequence(sim, ::sandSimulationStep).dropWhile { it.currentSand.y<=maxY }.first()

        return finalSim.settledSand.size

    }

    fun sandSimulationStep(sim: Simulation): Simulation{
        val (env, currentSand, settledSand) = sim
        return if(sim.isSettled(currentSand)){
            settledSand.add(currentSand)
            Simulation(env, env.newSand(), settledSand)
        } else {
            Simulation(env, sim.move(currentSand), settledSand)
        }
    }

}