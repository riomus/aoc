package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor
import java.lang.Math.min

open class T192 : T191() {

    open fun solve(file: String, steps: Long, blueprintsCount: Int): Long{
        val blueprints = data(file).flatMap { it.split(". ", ": ") }.windowed(5, 5, true).map { lines ->
                val splited = lines.map { it.split(" ") }
                BluePrint(splited[1][4].toLong(), splited[2][4].toLong(), ObsidianRobotCost(splited[3][4].toLong(), splited[3][7].toLong()), GeodeRobotCost(splited[4][4].toLong(), splited[4][7].toLong()))
            }.take(blueprintsCount)
        val maxObsidianForPrints = blueprints.mapIndexed { idx, blueprint ->
            println("Blueprint $idx")
            solve(blueprint, steps)
        }
        return maxObsidianForPrints.reduce(Long::times)
        }

    }

