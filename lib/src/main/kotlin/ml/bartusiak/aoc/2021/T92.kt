package ml.bartusiak.aoc.`2021`

class T92 : T91() {


    companion object {
        fun solve(file: String = "T9.txt"): Int = T92().solve(file)
    }

    override fun solve(file: String): Int {
        val dataMap: Map<Int, Map<Int, Int>> = extractMap(file)
        val minimas = extractMinimas(dataMap)

        val (basins, _) = minimas.fold(Pair(listOf<Int>(), setOf<Pair<Int, Int>>())) { (basins, visited), (x, y, _) ->
            val current = Pair(x, y)
            if (visited.contains(current)) {
                Pair(basins, visited)
            } else {
                val (basin, visits) = visit(setOf(current), visited, 0, dataMap)
                Pair(basins + listOf(basin), visited + visits)
            }
        }
        val sortedBasins = basins.sortedDescending()
        return sortedBasins[0] * sortedBasins[1] * sortedBasins[2]
    }

    private tailrec fun visit(
        pointsToVisit: Set<Pair<Int, Int>>, visited: Set<Pair<Int, Int>>,
        currentSize: Int, data: Map<Int, Map<Int, Int>>
    ): Pair<Int, Set<Pair<Int, Int>>> {

        val yetNotVisited =
            pointsToVisit.asSequence().filterNot { visited.contains(it) }.filterNot { (x, y) -> data[x]!![y]!! == 9 }
                .toSet()

        return if (yetNotVisited.isEmpty()) {
            Pair(currentSize, visited)
        } else {
            val newToVisit = yetNotVisited.asSequence().flatMap { (x, y) ->
                moves.asSequence().map { (dx, dy) ->
                    Pair(x + dx, y + dy)
                }.filter { (newX, newY) ->
                    newX > -1 && newY > -1 && newX < data.size && newY < data[0]!!.size
                }.filterNot { (x, y) -> data[x]!![y]!! == 9 }
            }.toSet()
            return visit(newToVisit, visited + pointsToVisit, currentSize + yetNotVisited.size, data)
        }

    }

}




