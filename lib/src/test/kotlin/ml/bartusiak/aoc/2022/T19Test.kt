package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T19Test : StringSpec({

    "T191 should return correct result for test data " {
        T191().solve("2022/T191_test.txt",24) shouldBe 33
    }
    "T191 should return correct result " {
        T191().solve("2022/T191.txt", 24) shouldBe 1650L
    }
    "T192 should return correct result for test data " {
        T192().solve("2022/T191_test.txt",32,3) shouldBe 62*56
    }
    "T192 should return correct result " {
        T192().solve("2022/T191.txt",32,3) shouldBe 5824L
    }
})