package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T9Test : StringSpec({


    "T91 should return correct result for test data" {
        T91.solve("T9_test.txt") shouldBe 15
    }

    "T91 should return correct result" {
        T91.solve() shouldBe 560
    }



    "T92 should return correct result for test data" {
        T92.solve("T9_test.txt") shouldBe 1134
    }


    "T92 should return correct result " {
        T92.solve() shouldBe 959136
    }
})