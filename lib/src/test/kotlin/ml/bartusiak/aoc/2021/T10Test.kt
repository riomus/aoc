package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T10Test : StringSpec({


    "T101 should return correct result for test data" {
        T101.solve("T10_test.txt") shouldBe 26397L
    }

    "T101 should return correct result" {
        T101.solve() shouldBe 369105L
    }



    "T102 should return correct result for test data" {
        T102.solve("T10_test.txt") shouldBe 288957L
    }


    "T102 should return correct result " {
        T102.solve() shouldBe 3999363569L
    }
})