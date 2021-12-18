package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

open class T101 : AOCTask {

    companion object {
        fun solve(file: String = "T10.txt"): Long = T101().solve(file)
    }

    val pairs = mapOf("[" to "]", "(" to ")", "{" to "}", "<" to ">")
    val points = mapOf("]" to 57, ")" to 3, "}" to 1197, ">" to 25137)

    fun processLines(file: String): List<Pair<ArrayDeque<String>, Int?>> {
        return data(file)
            .map { it.toCharArray().map { it.toString() } }.map { line ->
                line.fold(Pair<ArrayDeque<String>, Int?>(ArrayDeque(), null)) { (stack, score), element ->
                    score?.let {
                        Pair(stack, score)
                    } ?: run {
                        if (pairs.keys.contains(element)) {
                            stack.addLast(element)
                            Pair(stack, score)
                        } else {
                            stack.removeLastOrNull()?.let { lastElement ->
                                if (pairs[lastElement]!! == element) {
                                    Pair(stack, score)
                                } else {
                                    Pair(stack, points[element]!!)
                                }
                            } ?: run {
                                stack.addLast(element)
                                Pair(stack, score)
                            }
                        }
                    }
                }
            }
    }

    open fun solve(file: String): Long {
        return processLines(file).sumOf { (_, score) -> score?.toLong() ?: 0L }
    }
}




