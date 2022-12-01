package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T17Test : StringSpec({

    "T171 should return correct result for test data" {
        T171.solve("2021/T17_test.txt") shouldBe 45L
    }

    "T171 should return correct result for 6,9" {
        T171().simulate(Pair(6, 9), T171().loadArea("2021/T17_test.txt")) shouldBe
                SimulationStep(
                    initialState = ProbeState(x = 0, y = 0, dx = 6, dy = 9),
                    probeState = ProbeState(x = 21, y = -10, dx = 0, dy = -11),
                    maxY = 45,
                    isCorrect = true,
                    shouldContinue = false
                )
    }

    "T171 should return correct result" {
        T171.solve("2021/T17.txt") shouldBe 3570L
    }
    "T172 should return correct result for test data" {
        T172.solve("2021/T17_test.txt") shouldBe 112L
    }
    "T172 should return correct result" {
        T172.solve("2021/T17.txt") shouldBe 1919L
    }

})