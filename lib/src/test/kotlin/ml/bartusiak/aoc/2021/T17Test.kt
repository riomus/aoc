package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T17Test: StringSpec({

    "T171 should return correct result for test data" {
        T171.solve("T17_test.txt") shouldBe 45L
    }

    "T171 should return correct result for 6,9" {
        T171().simulate(Pair(6,9), T171().loadArea("T17_test.txt")) shouldBe 45L
    }

    "T171 should return correct result" {
        T171.solve("T17.txt") shouldBe 435
    }
    "T172 should return correct result for test data" {
        T172.solve("T17_test.txt") shouldBe 112L
    }
    "T172 should return correct result" {
        T172.solve("T17.txt") shouldBe 2842
    }

})