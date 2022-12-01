package ml.bartusiak.aoc.`2021`

open class T102 : T101() {

    companion object {
        fun solve(file: String = "2021/T10.txt"): Long = T102().solve(file)
    }

    val completitionPoints = mapOf("]" to 2, ")" to 1, "}" to 3, ">" to 4)

    override fun solve(file: String): Long {
        val scores = processLines(file).filter { (_, score) -> score == null }.map { (stack, score) ->
            val result: Long = stack.reversed().fold(0L) { acc, element ->
                acc * 5L + completitionPoints[pairs[element]]!!
            }
            result
        }
        return scores.sorted()[scores.size / 2]
    }
}




