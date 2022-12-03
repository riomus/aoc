package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T3Test : StringSpec({

    "T31 should return correct result for test data" {
        T31.solve("2022/T31_test.txt") shouldBe 157
    }

    "T31 should return correct result" {
        T31.solve("2022/T31.txt") shouldBe 7875
    }


    "T32 should return correct result  for test data" {
        T32.solve("2022/T31_test.txt") shouldBe 70
    }

    "T32 should return correct result" {
        T32.solve("2022/T31.txt") shouldBe 2479
    }

})