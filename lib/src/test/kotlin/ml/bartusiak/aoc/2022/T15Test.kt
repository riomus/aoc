package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T15Test : StringSpec({

    "T151 should return correct result for test data " {
        T151().solve("2022/T151_test.txt", 10) shouldBe 26
    }
    "T151 should return correct result " {
        T151().solve("2022/T151.txt", 2000000) shouldBe 5240818L
    }

    "T152 should return correct result for test data " {
        T152().solve("2022/T151_test.txt", 20) shouldBe 56000011
    }

    "T152 should return correct result  " {
        T152().solve("2022/T151.txt", 4000000) shouldBe 13213086906101L
    }
})