package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor

open class T182 : AOCTask {

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

        fun inRange(lower: SpacePoint, upper: SpacePoint): Boolean{
            return x<=upper.x && x>= lower.x &&
                y<=upper.y && y>= lower.y &&
                z<=upper.z && z>= lower.z
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
        }.toSet()

        val minX = cubes.minOf { it.x }-1
        val minY = cubes.minOf { it.y }-1
        val minZ = cubes.minOf { it.z }-1

        val endX = cubes.maxOf { it.x }+1
        val endY = cubes.maxOf { it.y }+1
        val endZ = cubes.maxOf { it.z }+1

        val endCube = SpacePoint(endX, endY, endZ)

        val start = SpacePoint(minX, minY, minZ)

        val searchQueue = ArrayDeque(listOf(start))
        val pointsSet = mutableSetOf<SpacePoint>()
        val seen = mutableSetOf<SpacePoint>()
        while(searchQueue.size>0){
            val point = searchQueue.removeFirst()
            if(point !in cubes && point.inRange(start, endCube) && point !in seen){
                pointsSet.add(point)
                searchQueue.addAll(point.neighbours())
            }
            seen.add(point)
        }

        val world = World(cubes)

        return world.emptySides().filter { it in pointsSet }.size
        }

    }

