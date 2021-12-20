package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T20Test : StringSpec({


    "T201 should return correct result for test data" {
        T201.solve("T20_test.txt") shouldBe 35L
    }

    "T201 should return correct result" {
        T201.solve() shouldBe 5819L
    }

    "T202 should return correct result for test data" {
        T202.solve("T20_test.txt", 50) shouldBe 3351L
    }

    "T202 should return correct result " {
        T202.solve("T20.txt") shouldBe 18516
    }


    "T202 should return correct result for conway " {
        T202.solve("T20_conway.txt") shouldBe 64L
    }
})