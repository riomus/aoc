package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T12Test : StringSpec({

    "T121 should return correct result for test data " {
        T121().solve("2022/T121_test.txt") shouldBe 31
    }
    "T121 should return correct result  " {
        T121().solve("2022/T121.txt") shouldBe 412L
    }
    "T122 should return correct result for test data " {
        T122().solve("2022/T121_test.txt") shouldBe 29
    }
    "T122 should return correct result  " {
        T122().solve("2022/T121.txt") shouldBe 402L
    }
})