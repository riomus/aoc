package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T11Test : StringSpec({

    "T111 should return correct result for small test data" {
        T111.solve("T11_test_small.txt", 2) shouldBe 9
    }

    "T111 should return correct result for test data and 2 steps" {
        T111.solve("T11_test.txt", 2) shouldBe 35
    }
    "T111 should return correct result for test data and 10 steps" {
        T111.solve("T11_test.txt", 10) shouldBe 204
    }
    "T111 should return correct result for test data" {
        T111.solve("T11_test.txt") shouldBe 1656
    }
    "T111 should return correct result" {
        T111.solve("T11.txt") shouldBe 1681
    }

    "T112 should return correct result for test data" {
        T112.solve("T11_test.txt") shouldBe 195
    }
    "T112 should return correct result" {
        T112.solve("T11.txt") shouldBe 276
    }

})