package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T21Test : StringSpec({

    "T211 should return correct result for test data " {
        T211().solve("2022/T211_test.txt") shouldBe 152
    }
    "T211 should return correct result" {
        T211().solve("2022/T211.txt") shouldBe 152
    }
    "T212 should return correct result for test data " {
        T212().solve("2022/T211_test.txt") shouldBe 301
    }
    "T212 should return correct result" {
        T212().solve("2022/T211.txt") shouldBe 3305669217840L
    }
})