package ml.bartusiak.aoc.`2021`

import java.util.*

open class T162 : T161() {

    companion object {
        fun solve(file: String = "T16.txt"): Long = T162().solve(file)
    }

    override fun solve(file: String): Long {
        val dataLines = data(file)
        var data: MutableList<Int> = dataLines.first().map { it.toString() }.map(this::toBinary)
            .flatMap { it.toCharArray().map { it.toString().toInt() } }.toMutableList()

        val result = generateSequence(
            ParsingState(LinkedList(data), listOf()),
            this::readNextPacket
        ).dropWhile { it.data.size > 0 }.first()

        return result.packets.first().value()
    }

}




