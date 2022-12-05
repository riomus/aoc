package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T5Test : StringSpec({

    "T51 should return correct result for test data" {
        T51().solve("2022/T51_test.txt") shouldBe "CMZ"
    }


    "T51 should return correct result " {
        T51().solve("2022/T51.txt") shouldBe "TQRFCBSJJ"
    }

    "T52 should return correct result for test data" {
        T52().solve("2022/T51_test.txt") shouldBe "MCD"
    }

    "T52 should return correct result" {
        T52().solve("2022/T51.txt") shouldBe "RMHFJNVFP"
    }



})