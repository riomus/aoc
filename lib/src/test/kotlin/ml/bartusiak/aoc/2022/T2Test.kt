package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T2Test : StringSpec({

    "T21 should return correct result for test data" {
        T21.solve("2022/T21_test.txt") shouldBe 15
    }

    "T21 should return correct result" {
        T21.solve("2022/T21.txt") shouldBe 12535
    }


    "T22 should return correct result  for test data" {
        T22.solve("2022/T21_test.txt") shouldBe 12
    }

    "T22 should return correct result" {
        T22.solve("2022/T21.txt") shouldBe 12535
    }

})