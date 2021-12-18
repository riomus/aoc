package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T2Test : StringSpec({

    "T21 should return correct result" {
        T21.solve() shouldBe 1698735
    }

    "T22 should return correct result" {
        T22.solve() shouldBe 1594785890
    }

})