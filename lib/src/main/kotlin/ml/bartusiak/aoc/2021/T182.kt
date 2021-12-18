package ml.bartusiak.aoc.`2021`

open class T182 : T181() {


    companion object {
        fun solve(file: String = "T17.txt"): Long = T182().solve(file)
    }

    override fun solve(file: String): Long {
        val data: List<SnailfishNumber> = loadNumbers(file)
        return data.flatMap { n1 ->
            data.flatMap { n2 -> listOf(listOf(n1, n2), listOf(n2, n1)) }
        }.map { it.sum() }.maxOf { it.magnitude() }
    }


}




