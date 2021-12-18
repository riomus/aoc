package ml.bartusiak.aoc.`2021`

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class T18Test : StringSpec({

    "T181 should parse data" {
        (T181()).parseNumber("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]") shouldBe
                Pair(
                    SnailPair(
                        left = SnailPair(
                            left = SnailPair(
                                left = Number(value = 0),
                                right = SnailPair(left = Number(value = 5), right = Number(value = 8))
                            ),
                            right = SnailPair(
                                left = SnailPair(left = Number(value = 1), right = Number(value = 7)),
                                right = SnailPair(left = Number(value = 9), right = Number(value = 6))
                            )
                        ),
                        right = SnailPair(
                            left = SnailPair(
                                left = Number(value = 4),
                                right = SnailPair(left = Number(value = 1), right = Number(value = 2))
                            ),
                            right = SnailPair(
                                left = SnailPair(left = Number(value = 1), right = Number(value = 4)),
                                right = Number(value = 2)
                            )
                        )
                    ), ""
                )
    }

    "T181 should return correctly explode data" {
        T181().parseNumber("[[[[[9,8],1],2],3],4]").first.explode(0) shouldBe AddOp(
            left = 9,
            right = 0,
            newChild = T181().parseNumber("[[[[0,9],2],3],4]").first
        )
    }
    "T181 should return correctly explode data 2" {
        T181().parseNumber("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").first.explode(0) shouldBe
                AddOp(left = 0, right = 0, newChild = T181().parseNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").first)
    }
    "T181 should return correctly explode data 3" {
        T181().parseNumber("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]").first.explode(0) shouldBe AddOp(
            left = 0,
            right = 0,
            newChild = T181().parseNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").first
        )
    }
    "T181 should return correct magnitude" {
        T181().parseNumber("[[1,2],[[3,4],5]]").first.magnitude() shouldBe 143
    }
    "T181 should return correct magnitude 2" {
        T181().parseNumber("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").first.magnitude() shouldBe 3488
    }
//
    "T181 should correctly sum 6" {
        T181().sum("T18_test_sum_6.txt") shouldBe T181().parseNumber("[[[[5,0],[7,4]],[5,5]],[6,6]]").first
    }

    "T181 should correctly sum 5" {
        T181().sum("T18_test_sum_5.txt") shouldBe T181().parseNumber("[[[[3,0],[5,3]],[4,4]],[5,5]]").first
    }

    "T181 should correctly sum 4" {
        T181().sum("T18_test_sum_4.txt") shouldBe T181().parseNumber("[[[[1,1],[2,2]],[3,3]],[4,4]]").first
    }

    "T181 should correctly sum simple" {
        T181().sum("T18_test_sum_simple.txt") shouldBe T181().parseNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").first
    }

    "T181 should correctly sum s1" {
        T181().sum("T18_test_sum_s1.txt") shouldBe T181().parseNumber("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]").first
    }

    "T181 should return correct result" {
        T181.solve("T18.txt") shouldBe 4057L
    }
    "T182 should return correct result for test data" {
        T182.solve("T18_test.txt") shouldBe 3993
    }
    "T182 should return correct result" {
        T182.solve("T18.txt") shouldBe 4683L
    }

})