package ml.bartusiak.aoc.`2022`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T6Test : StringSpec({

    "T61 should return correct result for test data 1" {
        T61().solveString("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5
        T61().solveString("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6
        T61().solveString("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10
        T61().solveString("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11
    }
    "T61 should return correct result" {
        T61().solve() shouldBe 1566
    }

    "T62 should return correct result for test data 1" {
        T62().solveString("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 19
        T62().solveString("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 23
        T62().solveString("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 29
        T62().solveString("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 26
    }
    "T62 should return correct result" {
        T62().solve() shouldBe 2265
    }


})