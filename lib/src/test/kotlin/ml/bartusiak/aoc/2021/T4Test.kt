package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T4Test : StringSpec({


    "T41 should return correct result for test data" {
        T41.solve("T4_test.txt") shouldBe 4512
    }

    "T41 should return correct result" {
        T41.solve() shouldBe 35711
    }


    "T42 should return correct result for test data" {
        T42.solve("T4_test.txt") shouldBe 1924
    }

    "T42 should return correct result " {
        T42.solve("T4.txt") shouldBe 5586
    }

})