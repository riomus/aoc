package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T13Test : StringSpec({

    "T131 should return correct result for test data " {
        T131().solve("2022/T131_test.txt") shouldBe 13
    }
    "T131 should return correct result  " {
        T131().solve("2022/T131.txt") shouldBe 5529
    }

    "T132 should return correct result for test data " {
        T132().solve("2022/T131_test.txt") shouldBe 140
    }
    "T132 should return correct result " {
        T132().solve("2022/T131.txt") shouldBe 27690
    }

})