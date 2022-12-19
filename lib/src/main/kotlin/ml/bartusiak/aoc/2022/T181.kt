package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor

open class T181 : AOCTask {

    data class SpacePoint(val x: Long, val y: Long, val z: Long){

        companion object{
            val neighbourMoves = listOf(
                SpacePoint(1, 0, 0),
                SpacePoint(0, 1, 0),
                SpacePoint(0, 0, 1),
                SpacePoint(-1, 0, 0),
                SpacePoint(0, -1, 0),
                SpacePoint(0, 0, -1),
            )
        }

        operator fun plus(other: SpacePoint): SpacePoint{
            return SpacePoint(this.x +other.x, this.y + other.y, this.z+other.z)
        }
        fun neighbours(): List<SpacePoint>  {
            return neighbourMoves.map { it + this }
        }
    }

    data class World(val cubes: Set<SpacePoint>) {
        fun emptySides(): List<SpacePoint> {
            return cubes.flatMap { it.neighbours() }.filter { !cubes.contains(it) }
        }
    }
    open fun solve(file: String,stonesToWait : Long): Int{
        val cubes = data(file).map{ line ->
            val splited = line.split(",").map{it.toLong()}
            SpacePoint(splited[0],splited[1],splited[2])
        }
        val world = World(cubes.toSet())

        return world.emptySides().size
        }

    }

