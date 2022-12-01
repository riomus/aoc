package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T22Test : StringSpec({


    "T221 should return correct result for test data" {
        T221.solve("2021/T22_test.txt") shouldBe 590784
    }

    "T221 should return correct result for test data T2" {
        T221.solve("2021/T22_test_3.txt") shouldBe 590784
    }
    "T221 should return correct result for simple test data" {
        T221.solve("2021/T22_test_simple.txt") shouldBe 1000L
    }

    "T221 should return correct result for simple test data 2" {
        T221.solve("2021/T22_test_simple_2.txt") shouldBe 2000L
    }
    "T221 should return correct result for simple test data 3" {
        T221.solve("2021/T22_test_simple_3.txt") shouldBe 1000L
    }
    "T221 should return correct result for simple test data 5" {
        T221.solve("2021/T22_test_simple_5.txt") shouldBe 875L
    }

    "T221 should return correct result" {
        T221.solve() shouldBe 610196
    }


    "T222 should return correct result for simple test data" {
        T222.solve("2021/T22_test_simple.txt") shouldBe 1000L
    }

    "T222 should return correct result for simple test data 2" {
        T222.solve("2021/T22_test_simple_2.txt") shouldBe 2000L
    }
    "T222 should return correct result for simple test data 3" {
        T222.solve("2021/T22_test_simple_3.txt") shouldBe 1000L
    }
    "T222 should return correct result for simple test data 4" {
        T222.solve("2021/T22_test_simple_4.txt") shouldBe 1000L
    }
    "T222 should return correct result for simple test data 5" {
        T222.solve("2021/T22_test_simple_5.txt") shouldBe 875
    }

    "T222 should return correct result for T1 test data" {
        T222.solve("2021/T22_test_3.txt") shouldBe 590784
    }
    "T222 should return correct result for test data" {
        T222.solve("2021/T22_test_2.txt") shouldBe 2758514936282235L
    }

    "T222 should return correct result " {
        T222.solve("2021/T22.txt") shouldBe 1282401587270826L
    }


})