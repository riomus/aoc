package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T23Test : StringSpec({


    "T231 should return correct result for test data" {
        T231.solve("2021/T23_test.txt") shouldBe 12521
    }

    "T231 should return correct result" {
        T231.solve() shouldBe 12240
    }


    "T232 should return correct result for test data" {
        T232.solve("2021/T232_test.txt") shouldBe 44169L
    }

    "T232 should return correct result " {
        T232.solve("2021/T232.txt") shouldBe 44618L
    }


})