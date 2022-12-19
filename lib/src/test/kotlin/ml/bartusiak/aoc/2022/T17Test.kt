package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T17Test : StringSpec({

    "T171 should return correct result for test data " {
        T171().solve("2022/T171_test.txt", 2022) shouldBe 3068
    }
    "T171 should return correct result " {
        T171().solve("2022/T171.txt", 2022) shouldBe 3177L
    }

    "T172 should return correct result for test data " {
        T172().solve("2022/T171_test.txt", 1000000000000L, 155L, 35L, 289L-236L, 236L, 36L, 4L) shouldBe 1514285714288
    }
    "T172 should return correct resul" {
        T172().solve("2022/T171.txt", 1000000000000L, 1050L, 2792-1052, 4380-1656, 1656, 6094L, 1L) shouldBe 1565517241382
    }

})