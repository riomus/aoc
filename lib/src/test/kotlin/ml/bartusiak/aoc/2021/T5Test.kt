package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T5Test: StringSpec({


    "T51 should return correct result for test data" {
        T51.solve("T5_test.txt") shouldBe 5
    }

    "T51 should return correct result" {
        T51.solve() shouldBe 7142
    }



    "T52 should return correct result for test data" {
        T51.solve("T5_test.txt", true) shouldBe 12
    }


    "T52 should return correct result " {
        T51.solve("T5.txt", true) shouldBe 20012
    }
})