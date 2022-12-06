package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil



open class T62 : AOCTask {
    fun solve(file: String = "2022/T61.txt"): Int {
        val line = data(file).first()
        return solveString(line)
    }
    fun solveString(line: String): Int {

        val windowSize = 14
        val windowIndex = line.windowed(windowSize).indexOfFirst { window ->
            window.toSet().size == window.length
        }
        return windowSize+windowIndex
    }

}