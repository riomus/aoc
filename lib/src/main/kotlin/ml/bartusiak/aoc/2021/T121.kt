package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

open class T121 : AOCTask {

    companion object {
        fun solve(file: String = "T12.txt"): Int = T121().solve(file, false)
    }


    open fun solve(file: String, timesToVisitSingleSmall: Boolean): Int {
        val graph: Map<String, Set<String>> = loadCaves(file)
        val paths: List<List<String>> = findPaths(
            graph,
            "start",
            "end",
            graph["start"]!!.map { listOf("start", it) },
            listOf(),
            timesToVisitSingleSmall
        )
        return paths.size
    }

    fun String.isLower(): Boolean {
        return this.lowercase() == this
    }

    tailrec fun findPaths(
        graph: Map<String, Set<String>>, start: String, end: String, currentPaths: List<List<String>>,
        finishedPaths: List<List<String>>, timesToVisitSingleSmall: Boolean
    ): List<List<String>> {
        val unfinishedPaths = currentPaths.filterNot { it.last() == end }
        val nowFinishedPath = currentPaths.filter { it.last() == end }
        val maxVisitsForSmall = if (timesToVisitSingleSmall) 2 else 1
        val continuedPaths: List<List<String>> = unfinishedPaths.flatMap { path ->
            val nextNodes = graph[path.last()]!!
            val correctNextNodes = nextNodes.filter { node ->
                val lowerNodeRule = node.isLower() &&
                        path.count { it == node } < maxVisitsForSmall &&
                        (!timesToVisitSingleSmall || (path + listOf(node)).filter { it.isLower() }.groupingBy { it }
                            .eachCount().values.count { it >= maxVisitsForSmall } < 2)
                node != start && (lowerNodeRule || !node.isLower())
            }
            correctNextNodes.map { path + listOf(it) }
        }
        return if (continuedPaths.isEmpty()) {
            finishedPaths + nowFinishedPath
        } else {
            findPaths(graph, start, end, continuedPaths, nowFinishedPath + finishedPaths, timesToVisitSingleSmall)
        }
    }

    fun loadCaves(file: String): Map<String, Set<String>> {
        return data(file).map { it.split("-") }
            .map { Pair(it[0], it[1]) }.flatMap { listOf(it, Pair(it.second, it.first)) }
            .groupBy { it.first }.mapValues { it.value.map { it.second }.toSet() }
    }

}




