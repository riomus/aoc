package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T14Test : StringSpec({

    "T141 should return correct result for test data " {
        T141().solve("2022/T141_test.txt") shouldBe 24
    }

    "T141 should return correct result " {
        T141().solve("2022/T141.txt") shouldBe 828
    }

    "T142 should return correct result for test data " {
        T142().solve("2022/T141_test.txt") shouldBe 93
    }

    "T142 should return correct result " {
        T142().solve("2022/T141.txt") shouldBe 25500
    }
})