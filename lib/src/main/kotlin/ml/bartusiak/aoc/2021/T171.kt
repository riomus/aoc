package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.max


data class Area(val minX: Long, val minY: Long, val maxX: Long, val maxY: Long)
data class ProbeState(val x: Long, val y: Long, val dx: Long, val dy: Long)
data class SimulationStep(
    val initialState: ProbeState,
    val probeState: ProbeState,
    val maxY: Long,
    val isCorrect: Boolean,
    val shouldContinue: Boolean
)

open class T171 : AOCTask {

    companion object {
        fun solve(file: String = "2021/T17.txt"): Long = T171().solve(file)
    }

    open fun solve(file: String): Long {
        val area: Area = loadArea(file)
        val maxY: Sequence<Long> = findSteps(area).map { it.maxY }
        return maxY.maxOrNull()!!
    }

    fun simulationStep(step: SimulationStep, area: Area): SimulationStep {
        val (initial, state, maxY, _, _) = step
        val newProbeState = ProbeState(
            state.x + state.dx, state.y + state.dy, if (state.dx == 0L) {
                0L
            } else if (state.dx < 0) {
                state.dx + 1
            } else {
                state.dx - 1
            }, state.dy - 1
        )
        val isCorrect = newProbeState.x <= area.maxX && newProbeState.y >= area.minY
        val isInArea =
            newProbeState.x <= area.maxX && newProbeState.y >= area.minY && newProbeState.x >= area.minX && newProbeState.y <= area.maxY
        val shouldContinue = isCorrect && !isInArea
        return SimulationStep(initial, newProbeState, max(maxY, newProbeState.y), isCorrect, shouldContinue)
    }

    fun simulate(initD: Pair<Long, Long>, area: Area): SimulationStep {
        val initState = ProbeState(0L, 0L, initD.first, initD.second)
        val step = { state: SimulationStep -> simulationStep(state, area) }
        val result =
            generateSequence(
                SimulationStep(initState, initState, 0L, isCorrect = true, shouldContinue = true),
                step
            ).dropWhile { it.shouldContinue }.first()
        return result
    }

    fun findSteps(area: Area): Sequence<SimulationStep> {

        val startVelocities = (0L..area.maxX).asSequence().flatMap { dx ->
            (-1 * abs(area.minY)..abs(area.minY)).map { dy ->
                Pair(dx, dy)
            }
        }

        return startVelocities.map { p -> simulate(p, area) }.filter { it.isCorrect }
    }

    fun loadArea(file: String): Area {
        val line = data(file).first()
        val xs = line.split(",").first().split("=").last().split("..").map { it.toLong() }
        val ys = line.split(",").last().split("=").last().split("..").map { it.toLong() }
        return Area(xs.first(), ys.first(), xs.last(), ys.last())
    }


}




