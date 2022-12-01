package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor


open class T192 : T191() {


    companion object {
        fun solve(file: String = "2021/T19.txt", minimalCommon: Int=12): Long = T192().solve(file, minimalCommon)
    }

    override fun solve(file: String, minimalCommon: Int): Long {
        val scanners = loadScanners(file)
        val singeBaseScanners =  toSingleBase(scanners, minimalCommon)
        val centers =  singeBaseScanners.map { it.center }
        return centers.flatMap { center ->
            centers.map{ secondCenter ->
                center.manhatan(secondCenter)
            }
        }.maxOrNull()!!.toLong()
    }
}




