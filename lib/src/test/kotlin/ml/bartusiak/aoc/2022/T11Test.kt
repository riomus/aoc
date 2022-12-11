package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T11Test : StringSpec({

    "T111 should return correct result for test data " {
        T111().solve("2022/T111_test.txt") shouldBe 10605
    }
    "T111 should return correct result  " {
        T111().solve("2022/T111.txt") shouldBe 61503L
    }


    "T112 should return correct result for test data " {
        T111().solve("2022/T111_test.txt", 10000) { l -> l } shouldBe 2713310158L
    }
    "T112 should return correct result " {
        T111().solve("2022/T111.txt", 10000) { l -> l } shouldBe 2713310158L
    }
})