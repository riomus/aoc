package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T3Test: StringSpec({

    "T31 should return correct result" {
        T31.solve() shouldBe 1997414
    }

    "T32 should return correct result for test data" {
        T32.solve("T3_test.txt") shouldBe 230
    }
    "T32 should return correct result" {
        T32.solve() shouldBe 1032597
    }

})