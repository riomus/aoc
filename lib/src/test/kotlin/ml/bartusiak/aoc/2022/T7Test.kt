package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T7Test : StringSpec({

    "T71 should return correct result for test data " {
        T71().solve("2022/T71_test.txt") shouldBe 95437L
    }

    "T71 should return correct result" {
        T71().solve("2022/T71.txt") shouldBe 1443806
    }

    "T72 should return correct result for test data " {
        T72().solve("2022/T71_test.txt") shouldBe 24933642L
    }
    "T72 should return correct result " {
        T72().solve("2022/T71.txt") shouldBe 942298L
    }
})