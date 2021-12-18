package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T6Test : StringSpec({


    "T61 should return correct result for test data" {
        T61.solve("T6_test.txt") shouldBe 5934L
    }

    "T61 should return correct result" {
        T61.solve() shouldBe 374927L
    }



    "T62 should return correct result for test data" {
        T61.solve("T6_test.txt", 256) shouldBe 26984457539L
    }


    "T62 should return correct result " {
        T61.solve("T6.txt", 256) shouldBe 1687617803407L
    }
})