package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T16Test : StringSpec({

    "T161 should return correct result for test data " {
        T161().solve("2022/T161_test.txt") shouldBe 1651
    }
    "T161 should return correct result " {
        T161().solve("2022/T161.txt" ) shouldBe 1584L
    }
    "T162 should return correct result for test data " {
        T162().solve("2022/T161_test.txt") shouldBe 1707
    }
    "T162 should return correct result " {
        T162().solve("2022/T161.txt") shouldBe 2052
    }
})