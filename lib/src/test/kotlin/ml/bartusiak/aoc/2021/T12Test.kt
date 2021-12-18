package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T12Test : StringSpec({

    "T121 should return correct result for test data" {
        T121.solve("T12_test.txt") shouldBe 10
    }
    "T121 should return correct result for large test data" {
        T121.solve("T12_test_large.txt") shouldBe 19
    }
    "T121 should return correct result" {
        T121.solve("T12.txt") shouldBe 3563
    }
    "T122 should return correct result for test data" {
        T122.solve("T12_test.txt") shouldBe 36
    }
    "T122 should return correct result for large test data" {
        T122.solve("T12_test_large.txt") shouldBe 103
    }
    "T122 should return correct result" {
        T122.solve("T12.txt") shouldBe 105453
    }

})