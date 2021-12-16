package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import java.util.*

import kotlin.reflect.KFunction1

data class Packet(val version: Int, val typeId: Int, val subPackets: List<Packet>, val literals: List<Long>) {

    fun sumVersions(): Int = version + subPackets.sumOf { it.sumVersions() }

    fun value(): Long {
        return when (typeId) {
            4 -> literals.sum()
            0 -> subPackets.sumOf { it.value() }
            1 -> subPackets.fold(1) { acc, p -> acc * p.value() }
            2 -> subPackets.minOf { it.value() }
            3 -> subPackets.maxOf { it.value() }
            5 -> if (subPackets[0]!!.value() > subPackets[1]!!.value()) 1L else 0L
            6 -> if (subPackets[0]!!.value() < subPackets[1]!!.value()) 1L else 0L
            7 -> if (subPackets[0]!!.value() == subPackets[1]!!.value()) 1L else 0L
            else -> throw Exception("WaT! Type id unknown $typeId")
        }
    }

}

data class ParsingState(val data: LinkedList<Int>, val packets: List<Packet>)
open class T161 : AOCTask {

    companion object {
        fun solve(file: String = "T16.txt"): Long = T161().solve(file)
    }

    open fun solve(file: String): Long {
        val dataLines = data(file)
        var data: MutableList<Int> = dataLines.first().map { it.toString() }.map(this::toBinary)
            .flatMap { it.toCharArray().map { it.toString().toInt() } }.toMutableList()

        val result = generateSequence(
            ParsingState(LinkedList(data), listOf()),
            this::readNextPacket
        ).dropWhile { it.data.size > 0 }.first()

        return result.packets.sumOf { it.sumVersions() }.toLong()
    }

    fun Queue<Int>.poll(n: Int): List<Int> {
        return (1..n).map { this.poll() }
    }

    fun parsePacket(newData: LinkedList<Int>): Packet {

        val version = newData.poll(3).joinToString("").toInt(2)
        val typeId = newData.poll(3).joinToString("").toInt(2)

        val (literals: MutableList<Long>, subPackets: MutableList<Packet>) = if (typeId == 4) {
            var lastPacket = newData.poll(5)
            var literals = emptyList<Int>().toMutableList()
            literals.addAll(lastPacket.drop(1))
            while (lastPacket.first()!! != 0) {
                lastPacket = newData.poll(5)
                literals.addAll(lastPacket.drop(1))
            }
            Pair(listOf(literals.joinToString("").toLong(2)).toMutableList(), emptyList<Packet>().toMutableList())
        } else {
            var subPackets = emptyList<Packet>().toMutableList()
            val mode = newData.poll()
            if (mode == 1) {
                var subPacketCounts = newData.poll(11).joinToString("").toInt(2)
                subPackets.addAll((1..subPacketCounts).map { parsePacket(newData) })
            } else {
                var subPacketTotal = newData.poll(15).joinToString("").toInt(2)
                var extracted = 0
                var currentLength = newData.size
                while (extracted != subPacketTotal) {
                    subPackets.add(parsePacket(newData))
                    extracted += currentLength - newData.size
                    currentLength = newData.size
                }
            }
            Pair(emptyList<Long>().toMutableList(), subPackets)
        }
        val newPacket = Packet(version, typeId, subPackets, literals)
        return newPacket
    }

    fun readNextPacket(state: ParsingState): ParsingState {
        val (data, packets) = state
        val newPacket = parsePacket(data)
        if (!data.contains(1)) {
            data.clear()
        }
        return ParsingState(data, packets + listOf(newPacket))
    }

    val hexMap = mapOf(
        "0" to "0000",
        "1" to "0001",
        "2" to "0010",
        "3" to "0011",
        "4" to "0100",
        "5" to "0101",
        "6" to "0110",
        "7" to "0111",
        "8" to "1000",
        "9" to "1001",
        "A" to "1010",
        "B" to "1011",
        "C" to "1100",
        "D" to "1101",
        "E" to "1110",
        "F" to "1111"
    )

    fun toBinary(entry: String): String = hexMap[entry]!!
}




