package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T9Test : StringSpec({

    "T91 should return correct result for test data " {
        T91().solve("2022/T91_test.txt") shouldBe 13
    }
    "T91 should return correct result for test data using listApproach " {
        T92().solve("2022/T91_test.txt", 1) shouldBe 13
    }
    "T91 should return correct result " {
        T91().solve("2022/T91.txt") shouldBe 6314
    }
    "T92 should return correct result for large test data" {
        T92().solve("2022/T91_test2.txt") shouldBe 36
    }
    "T92 should return correct result for test data" {
        T92().solve("2022/T91_test.txt") shouldBe 1
    }
    "T92 should return correct result " {
        T92().solve("2022/T91.txt") shouldBe 2504
    }
})