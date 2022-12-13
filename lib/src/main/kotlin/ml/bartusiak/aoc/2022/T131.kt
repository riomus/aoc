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


open class T131 : AOCTask  {

    sealed interface Packet{

        fun compareTo(other: Packet): Int
        data class Scalar(val value: Int): Packet {
            override fun compareTo(other: Packet): Int{
               return  when(other){
                    is Scalar -> value.compareTo(other.value)
                    is PacketList -> PacketList(listOf(this)).compareTo(other)
                }
            }
        }
        data class PacketList(val data: List<Packet>): Packet{
            override fun compareTo(other: Packet): Int{
                return  when(other){
                    is Scalar -> this.compareTo(PacketList(listOf(other)))
                    is PacketList -> {
                        val valueComparisions = data.zip(other.data).map { (l, r) ->
                            l.compareTo(r)
                        }
                        val valuesAreLower = valueComparisions.filter { it!=0 }.map{it<0}.firstOrNull()?:false
                        val valuesAreGreater = valueComparisions.filter { it!=0 }.map{it>0}.firstOrNull()?:false
                        val valuesAreEqual = valueComparisions.all { it==0 }
                        return  if(valuesAreLower){
                            -1
                        } else if(valuesAreGreater){
                            1
                        } else if(this.data.size<other.data.size){
                            -1
                        } else if(other.data.size<this.data.size){
                            1
                        }else {
                            require(valuesAreEqual)
                            0
                        }
                    }
                }
            }
        }
    }

    data class PacketsPair(val l: Packet, val r: Packet){
        fun inRightOrder(): Boolean{
            val result = l.compareTo(r)<=0
            return result
        }
    }

    fun JsonArray.toPacket(): Packet{
        val content = this.map { when(it){
            is JsonPrimitive -> Packet.Scalar(it.int)
            is JsonArray -> it.toPacket()
            else -> throw RuntimeException("unhandled element type: $it")
        } }
        return Packet.PacketList(content)
    }

    fun String.toPacket(): Packet{
        val json = Json.parseToJsonElement(this) as  JsonArray
        return json.toPacket()
    }

    fun parse(file: String): List<PacketsPair>{
        return data(file).windowed(3,3,true).map { group ->
            val l = group[0].toPacket()
            val r = group[1].toPacket()
            PacketsPair(l, r)
        }
    }

    open fun solve(file: String): Int {
        val packetsPairs = parse(file)
            val ordered= packetsPairs.map{it to it.inRightOrder()}
                return ordered.mapIndexedNotNull{i, (p, ordered) ->
                if(ordered){
                     i+1
                }else {
                    null
                }
            }.sum()

    }

}