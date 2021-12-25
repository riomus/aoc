package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class T25Test : StringSpec({


    "T251 should return correct result for test data" {
        val result = T251.solve("T251_test.txt")
        result shouldNotBe  98399959992947
        result shouldBe  58L
    }

    "T251 should return correct result" {
        val result = T251.solve("T251.txt")
        result shouldBe  509L
    }


})