package ml.bartusiak.aoc.`2021`

open class T172 : T171() {

    companion object {
        fun solve(file: String = "2021/T17.txt"): Long = T172().solve(file)
    }

    override fun solve(file: String): Long {
        val area: Area = loadArea(file)
        val initStates = findSteps(area).map { it.initialState }.toSet()
        return initStates.size.toLong()
    }


}




