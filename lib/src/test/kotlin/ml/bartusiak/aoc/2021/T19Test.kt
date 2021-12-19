package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T19Test : StringSpec({


    "T191 should return correct result for test data" {
        T191.solve("T19_test.txt") shouldBe 79
    }


    "T191 should return correct result for small test data" {
        T191.solve("T19_test_small.txt", 6) shouldBe 6L
    }

    "T191 should return correct result" {
        T191.solve() shouldBe 392
    }



    "T192 should return correct result for test data" {
        T192.solve("T19_test.txt") shouldBe 3621L
    }


    "T192 should return correct result " {
        T192.solve("T19.txt") shouldBe 13332L
    }
})