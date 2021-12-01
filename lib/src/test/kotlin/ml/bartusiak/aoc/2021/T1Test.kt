package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T1Test: StringSpec({

    "T11 should return correct result" {
        T11.solve() shouldBe 1266
    }
    "T12 should return correct result" {
        T12.solve() shouldBe 1217
    }

})