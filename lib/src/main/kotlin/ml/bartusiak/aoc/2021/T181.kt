package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil
import kotlin.math.floor


sealed interface SnailfishNumber {

    operator fun plus(other: SnailfishNumber): SnailfishNumber {
        val added = SnailPair(this, other)
        val reduced = added.reduce()
        return reduced
    }

    fun addToLeft(number: Int): SnailfishNumber
    fun magnitude(): Long
    fun addToRight(number: Int): SnailfishNumber
    fun explode(level: Int): ExplodeOp = NoOp
    fun split(): SnailPair?


}

data class Number(val value: Int) : SnailfishNumber {

    override fun addToLeft(number: Int): SnailfishNumber = Number(number + value)

    override fun addToRight(number: Int): SnailfishNumber = addToLeft(number)

    override fun split(): SnailPair? {
        return if (value > 9) {
            val half: Double = (value / 2.0)
            SnailPair(Number(floor(half).toInt()), Number(ceil(half).toInt()))
        } else {
            null
        }
    }

    override fun magnitude(): Long {
        return value.toLong()
    }

    override fun toString(): String {
        return value.toString()
    }
}

sealed interface ExplodeOp
data class AddOp(val left: Int, val right: Int, val newChild: SnailfishNumber) : ExplodeOp
object NoOp : ExplodeOp
data class SnailPair(val left: SnailfishNumber, val right: SnailfishNumber) : SnailfishNumber {

    override fun toString(): String {
        return "[${left}, ${right}]"
    }

    fun reduce(): SnailfishNumber {
        return generateSequence(this) { it.reduceStep() }.windowed(2, 1).dropWhile {
            it.first() != it.last()
        }.first().first()
    }

    fun reduceStep(): SnailPair {
        val explodeRes = explodeOp(0)
        return explodeRes ?: run {
            split()
        } ?: this
    }

    fun explodeOp(level: Int): SnailPair? {
        val exploded = explode(level)
        return when (exploded) {
            is NoOp -> null
            is AddOp -> exploded.newChild as SnailPair
        }
    }

    override fun explode(level: Int): ExplodeOp {
        return if (level > 3 && left is Number && right is Number) {
            AddOp(left.value, right.value, Number(0))
        } else if (left is SnailPair && right is SnailPair) {
            val leftExplode = left.explode(level + 1)
            when (leftExplode) {
                is NoOp -> {
                    val rightExplodeResult = right.explode(level + 1)
                    when (rightExplodeResult) {
                        is NoOp -> NoOp
                        is AddOp -> AddOp(
                            0,
                            rightExplodeResult.right,
                            SnailPair(left.addToRight(rightExplodeResult.left), rightExplodeResult.newChild)
                        )
                    }
                }
                is AddOp -> AddOp(
                    leftExplode.left,
                    0,
                    SnailPair(leftExplode.newChild, right.addToLeft(leftExplode.right))
                )
            }
        } else if (left is SnailPair && right is Number) {
            val explodeResult = left.explode(level + 1)
            when (explodeResult) {
                is NoOp -> NoOp
                is AddOp -> AddOp(
                    explodeResult.left,
                    0,
                    SnailPair(explodeResult.newChild, right.addToLeft(explodeResult.right))
                )
            }
        } else if (right is SnailPair && left is Number) {
            val explodeResult = right.explode(level + 1)
            when (explodeResult) {
                is NoOp -> NoOp
                is AddOp -> {
                    val newChild = SnailPair(left.addToRight(explodeResult.left), explodeResult.newChild)
                    AddOp(0, explodeResult.right, newChild)
                }
            }
        } else {
            NoOp
        }
    }

    override fun split(): SnailPair? {
        return left.split()?.let {
            SnailPair(it, right)
        } ?: right.split()?.let {
            SnailPair(left, it)
        }
    }

    override fun addToLeft(number: Int): SnailfishNumber {
        return SnailPair(left.addToLeft(number), right)
    }

    override fun addToRight(number: Int): SnailfishNumber {
        return SnailPair(left, right.addToRight(number))
    }

    override fun magnitude(): Long {
        return 3L * left.magnitude() + 2L * right.magnitude()
    }
}

open class T181 : AOCTask {


    companion object {
        fun solve(file: String = "T17.txt"): Long = T181().solve(file)
    }

    fun List<SnailfishNumber>.sum(): SnailfishNumber = this.reduce { n1, n2 ->
        n1 + n2
    }

    fun sum(file: String): SnailfishNumber {
        val data: List<SnailfishNumber> = loadNumbers(file)
        return data.sum()
    }

    open fun solve(file: String): Long {

        return sum(file).magnitude()
    }

    fun loadNumbers(file: String): List<SnailfishNumber> {
        val lines = data(file)
        return lines.map(this::parseNumber).map { it.first }
    }

    fun parseNumber(repr: String): Pair<SnailfishNumber, String> {
        val filtered = repr.replace("]", "")
        return if (filtered.first().toString() == "[") {
            val (left, rest) = parseNumber(filtered.drop(1))
            val (right, afterRight) = parseNumber(rest.drop(1))
            Pair(SnailPair(left, right), afterRight)
        } else {
            Pair(Number(filtered.first().toString().toInt()), filtered.drop(1))
        }
    }


}




