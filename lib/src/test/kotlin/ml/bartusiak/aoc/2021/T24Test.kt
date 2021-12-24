package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class T24Test : StringSpec({


    "T241 should return correct result" {
        val result = T241.solve("T24.txt")
        result shouldNotBe  98399959992947
        result shouldBe  74929995999389L
    }



    "T242 should return correct result " {
        val result = T242.solve("T24.txt")
        result shouldNotBe  98399959992947
        result shouldBe  11118151637112L
    }


})