package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T21Test : StringSpec({


    "T211 should return correct result for test data" {
        T211.solve("2021/T21_test.txt") shouldBe 739785L
    }

    "T211 should return correct result" {
        T211.solve() shouldBe 853776L
    }

    "T212 should return correct result for test data" {
        T212.solve("2021/T21_test.txt") shouldBe 444356092776315L
    }

    "T212 should return correct result " {
        T212.solve("2021/T21.txt") shouldBe 301304993766094L
    }


})