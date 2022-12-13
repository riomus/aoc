package ml.bartusiak.aoc.`2022`

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import ml.bartusiak.aoc.AOCTask
import java.lang.RuntimeException
import java.util.Comparator
import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min


open class T132 : T131()  {

    override fun solve(file: String): Int {
        val p1 = Packet.PacketList(listOf(Packet.PacketList(listOf(Packet.Scalar(2)))))
        val p2 = Packet.PacketList(listOf(Packet.PacketList(listOf(Packet.Scalar(6)))))
        val packetsPairs = parse(file)
        val ordered = (packetsPairs.flatMap {
                listOf(it.r, it.l)
        } + p1 + p2).sortedWith(Packet::compareTo)
        val i1 = ordered.indexOf(p1)
        val i2 = ordered.indexOf(p2)
        return ((i1+1)*(i2+1))
    }

}