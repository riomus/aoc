package ml.bartusiak.aoc.`2021`


data class Board(val data: List<List<Pair<Int, Boolean>>>) {
    private fun rows(): List<List<Pair<Int, Boolean>>> = data
    private fun cols(): List<List<Pair<Int, Boolean>>> = (0 until 5).map { data.map { row -> row[it] } }
    fun bingo(): Boolean =
        rows().any { items -> items.all { it.second } } || cols().any { items -> items.all { it.second } }

    fun draw(toDraw: Int): Board =
        if (bingo()) {
            this
        } else {
            Board(data.map { items ->
                items.map {
                    val (item, selected) = it
                    Pair(item, item == toDraw || selected)
                }
            })
        }

    fun score(): Int {
        return if (bingo()) {
            data.sumOf { items -> items.filter { !it.second }.sumOf { it.first } }
        } else {
            0
        }
    }
}

