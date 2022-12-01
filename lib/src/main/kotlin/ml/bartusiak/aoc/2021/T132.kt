package ml.bartusiak.aoc.`2021`

open class T132 : T131() {

    companion object {
        fun solve(file: String = "2021/T13.txt"): String = T132().solveT2(file)
    }

    fun solveT2(file: String): String {
        val loadedData = data(file)
        val points = startPage(loadedData)
        val instructions = instructions(loadedData)

        val folded = instructions.fold(points, this::foldThePaper).toSet()

        val maxX = folded.maxOf { it.first }
        val maxY = folded.maxOf { it.second }

        val result = (0..maxY + 1).joinToString("\n") { y ->
            (0..maxX + 1).joinToString("") { x ->
                if (folded.contains(Pair(x, y))) {
                    "#"
                } else {
                    "."
                }
            }
        }
        return result
    }

}




