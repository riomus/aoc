package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.abs
import java.lang.Math.sqrt


open class T142 : T141() {

    override fun solve(file: String): Int {
        val sim = getSimulation(file)
        val maxY = sim.env.formations.formations.flatMap { it.rocks }.map{it as RockLine.StandardRockLine}.flatMap { listOf(it.start.y, it.end.y) }.max()
        val floorY = maxY +2
        val flooredSim = sim.copy(env = sim.env.copy(formations = sim.env.formations.copy(formations = sim.env.formations.formations + RockFormation(setOf(RockLine.Floor( floorY))))))
        val finalSim= generateSequence(flooredSim, ::sandSimulationStep).dropWhile { !it.settledSand.contains(PlanePoint(500,0))}.first()

        return finalSim.settledSand.size

    }

}