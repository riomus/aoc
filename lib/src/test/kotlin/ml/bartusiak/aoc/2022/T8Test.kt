package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T8Test : StringSpec({

    "T81 should return correct result for test data " {
        T81().solve("2022/T81_test.txt") shouldBe 21
    }
    "T81 should return correct result " {
        T81().solve("2022/T81.txt") shouldBe 1672
    }

    "T82 should return correct result for test data " {
        T82().solve("2022/T81_test.txt") shouldBe 8
    }
    "T82 should return correct result " {
        T82().solve("2022/T81.txt") shouldBe 327180
    }
})