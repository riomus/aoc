package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T14Test : StringSpec({

    "T141 should return correct result for test data" {
        T141.solve("2021/T14_test.txt") shouldBe 1588
    }
    "T141 should return correct result" {
        T141.solve("2021/T14.txt") shouldBe 3284
    }
    "T142 should return correct result for test data" {
        T142.solve("2021/T14_test.txt") shouldBe 2188189693529L
    }
    "T142 should return correct result" {
        T142.solve("2021/T14.txt") shouldBe 4302675529689L
    }

})