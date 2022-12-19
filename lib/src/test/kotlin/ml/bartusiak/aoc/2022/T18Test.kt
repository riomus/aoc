package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T18Test : StringSpec({

    "T181 should return correct result for test data " {
        T181().solve("2022/T181_test.txt", 2022) shouldBe 64
    }
    "T181 should return correct result " {
        T181().solve("2022/T181.txt", 2022) shouldBe 3498
    }
    "T182 should return correct result for test data " {
        T182().solve("2022/T181_test.txt", 2022) shouldBe 58
    }
    "T182 should return correct result " {
        T182().solve("2022/T181.txt", 2022) shouldBe 3498
    }
})