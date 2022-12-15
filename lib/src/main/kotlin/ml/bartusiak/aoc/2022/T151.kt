package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

open class T151 : AOCTask {

//Sensor at x=2899860, y=3122031: closest beacon is at x=2701269, y=3542780

    data class PlenaryPoint(val x: Long, val y: Long){

        fun manhatan(other: PlenaryPoint): Long{
            return abs(x - other.x) + abs(y-other.y)
        }

        fun plusX(x: Long): PlenaryPoint{
            return PlenaryPoint(x+this.x, y)
        }

    }
    data class SensorBeaconPair(val sensor: PlenaryPoint, val beacon: PlenaryPoint){


        fun beaconsInDirection(start: PlenaryPoint, beaconsDistance: Long,  step: Long): LongRange{
            val end =  step*(beaconsDistance-abs(start.y-sensor.y))+sensor.x
           return  min(start.x, end-1) .. max(start.x, end-1)

        }
        fun notPresentBeaconsAt(row: Long): LongRange?{
            val start = PlenaryPoint(sensor.x, row)
            val beaconsDistance = sensor.manhatan(beacon)
            val startDistance = start.manhatan(sensor)
            return if(startDistance<=beaconsDistance){
                val right: LongRange = beaconsInDirection(start, beaconsDistance, 1)
                val left: LongRange = beaconsInDirection(start, beaconsDistance, -1)
                left.first.. right.last
            } else {
                null
            }
        }
    }

    fun  beacons(file:String): List<SensorBeaconPair> {
        return data(file).map{line ->
            val splited = line.split(":",", ","x=","y=")
            SensorBeaconPair(PlenaryPoint(splited[1].toLong(), splited[3].toLong()), PlenaryPoint(splited[5].toLong(), splited[7].toLong()))
        }
    }
    open fun solve(file: String, row: Long): Long{
        val pairs = beacons(file)
        val beaconSpots = pairs
            .mapNotNull { it.notPresentBeaconsAt(row) }
        val min = beaconSpots.minOf { it.first }
        val max = beaconSpots.maxOf { it.last }
        return (min .. max)
            .filter {n -> beaconSpots.any { it.contains(n) } }
            .fold(0L){acc, _ -> acc+1L}-1L

    }


}