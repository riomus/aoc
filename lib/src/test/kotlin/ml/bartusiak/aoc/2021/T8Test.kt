package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T8Test: StringSpec({


    "T91 should return correct result for test data" {
        T81.solve("T8_test.txt") shouldBe 26
    }

    "T81 should return correct result" {
        T81.solve() shouldBe 237
    }



    "T82 should return correct result for test data" {
        T82.solve("T8_test.txt") shouldBe 61229
    }


    "T82 should return correct result " {
        T82.solve("T8.txt") shouldBe 1009098
    }
})