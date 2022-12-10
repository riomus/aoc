package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T10Test : StringSpec({

    "T101 should return correct result for test data " {
        T101().solve("2022/T101_test.txt") shouldBe 13140
    }
    "T101 should return correct result " {
        T101().solve("2022/T101.txt") shouldBe 13740
    }

    "T102 should return correct result for test data" {
        T102().solveCrt("2022/T101_test.txt") shouldBe """
            
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
            
        """.trimIndent()
    }
    "T102 should return correct result " {
        T102().solveCrt("2022/T101.txt") shouldBe """
            
            ####.#..#.###..###..####.####..##..#....
            ...#.#..#.#..#.#..#.#....#....#..#.#....
            ..#..#..#.#..#.#..#.###..###..#....#....
            .#...#..#.###..###..#....#....#....#....
            #....#..#.#....#.#..#....#....#..#.#....
            ####..##..#....#..#.#....####..##..####.
            
        """.trimIndent()
    }

})