package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T20Test : StringSpec({

    "T201 should return correct result for test data " {
        T201().solve("2022/T201_test.txt",1, 1) shouldBe 3
    }
    "T201 should return correct result " {
        T201().solve("2022/T201.txt", 1, 1) shouldBe 4426
    }

    "T202 should return correct result for test data " {
        T201().solve("2022/T201_test.txt",10, 811589153) shouldBe 1623178306
    }
    "T202 should return correct result  " {
        T201().solve("2022/T201.txt",10, 811589153) shouldBe 8119137886612L
    }
})