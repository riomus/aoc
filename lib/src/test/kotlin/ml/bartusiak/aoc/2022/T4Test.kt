package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T4Test : StringSpec({

    "T41 should return correct result for test data" {
        T41.solve("2022/T41_test.txt") shouldBe 2
    }

    "T41 should return correct result" {
        T41.solve("2022/T41.txt") shouldBe 494
    }

    "T42 should return correct result for test data" {
        T42.solve("2022/T41_test.txt") shouldBe 4
    }

    "T42 should return correct result" {
        T42.solve("2022/T41.txt") shouldBe 494
    }

})