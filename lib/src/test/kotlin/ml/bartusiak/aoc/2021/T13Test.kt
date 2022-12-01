package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T13Test : StringSpec({

    "T131 should return correct result for test data" {
        T131.solve("2021/T13_test.txt") shouldBe 17
    }
    "T131 should return correct result" {
        T131.solve("2021/T13.txt") shouldBe 708
    }
    "T132 should return correct result for test data" {
        T132.solve("2021/T13_test.txt") shouldBe
                """#####.
                  |#...#.
                  |#...#.
                  |#...#.
                  |#####.
                  |......""".trimMargin()
    }
    "T132 should return correct result" {
        T132.solve("2021/T13.txt") shouldBe
                """####.###..#....#..#.###..###..####.#..#.
                                      |#....#..#.#....#..#.#..#.#..#.#....#..#.
                                      |###..###..#....#..#.###..#..#.###..####.
                                      |#....#..#.#....#..#.#..#.###..#....#..#.
                                      |#....#..#.#....#..#.#..#.#.#..#....#..#.
                                      |####.###..####..##..###..#..#.#....#..#.
                                      |........................................""".trimMargin()
    }

})