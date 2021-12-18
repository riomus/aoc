package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T15Test : StringSpec({

    "T151 should return correct result for test data" {
        T151.solve("T15_test.txt") shouldBe 40L
    }
    "T151 should return correct result for large test data" {
        T151.solve("T151_large.txt") shouldBe 315L
    }
    "T151 should return correct result" {
        T151.solve("T15.txt") shouldBe 435
    }
    "T152 should correctly enlarge  single digit" {
        val t = T152()
        t.loadMap("T151_extra_small_x_5.txt") shouldBe t.makeMapLarge(t.loadMap("T151_extra_small.txt"))
    }
    "T152 should correctly map data" {
        val t = T152()
        t.loadMap("T151_large.txt") shouldBe t.makeMapLarge(t.loadMap("T15_test.txt"))
    }
    "T152 should return correct result for test data" {
        T152.solve("T15_test.txt") shouldBe 315L
    }
    "T152 should return correct result" {
        T152.solve("T15.txt") shouldBe 2842
    }

})