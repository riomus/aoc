package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.util.*

class T16Test: StringSpec({

    "T161 should return correct result for simple test data" {
        T161.solve("T16_test_simple.txt") shouldBe 20
    }
    "T161 should correctly read literal packer" {
        val result =  T161().readNextPacket(ParsingState(LinkedList("110100101111111000101000".split("").filterNot{it.isEmpty()}.map { it.toInt() }), listOf()))
        val packet = result.packets.first()
        packet.version shouldBe  6
        packet.typeId shouldBe  4
        packet.literals.first() shouldBe  2021
    }
    "T161 should return correct result" {
        T161.solve("T16.txt") shouldBe 1012
    }
    "T162 should return correct result for test data" {
        T162.solve("T16_test_simple.txt") shouldBe 1
    }
    "T162 should return correct result" {
        T162.solve("T16.txt") shouldBe 2223947372407L
    }

})