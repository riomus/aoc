package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T7Test: StringSpec({


    "T71 should return correct result for test data" {
        T71.solve("T7_test.txt") shouldBe 37
    }

    "T71 should return correct result" {
        T71.solve() shouldBe 337833
    }



    "T72 should return correct result for test data" {
        T72.solve("T7_test.txt") shouldBe 168
    }


    "T72 should return correct result " {
        T72.solve("T7.txt") shouldBe 96678050
    }
})