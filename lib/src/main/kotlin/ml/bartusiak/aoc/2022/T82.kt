package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil



open class T82 : AOCTask {

    open fun solve(file: String = "2022/T81.txt"): Int {
        val grid = data(file).map { it.split("").filter { it.isNotEmpty() }.map { it.toInt() } }

        val scores = scores(grid)

        return scores.flatten().maxOf { it }
    }

    private fun scores(grid: List<List<Int>>): List<List<Int>> {
        return grid.mapIndexed{x, row ->
            row.mapIndexed { y, height ->
                score(height, x, y, grid)
            }
        }
    }

    private fun score(height: Int, x: Int, y: Int, grid: List<List<Int>>): Int {
        return scoreLeft(height, x, y, grid)*scoreRight(height, x, y, grid)*
            scoreTop(height, x, y, grid)*scoreDown(height, x, y, grid)
    }

    private fun scoreLeft(height: Int, x: Int, y: Int, grid: List<List<Int>>): Int {
        return if(y==0){
             0
        } else {
            val line = grid[x].take(y).reversed()
            val visible = line.takeWhile { it<height }.size
            visible+(if(line.size>visible) 1 else 0)
        }
    }
    private fun scoreRight(height: Int, x: Int, y: Int, grid: List<List<Int>>): Int {
        return if(y==grid.size-1){
             0
        } else {
            val line = grid[x].takeLast(grid.size-y-1)
            val visible = line.takeWhile { it<height }.size
            visible+if(line.size>visible) 1 else 0
        }
    }
    private fun scoreTop(height: Int, x: Int, y: Int, grid: List<List<Int>>): Int {
        return if(x==0){
            0
        } else {
            val line = grid.map { it[y] }.take(x).reversed()
            val visible = line.takeWhile { it<height }.size
            visible+if(line.size>visible) 1 else 0
        }
    }
    private fun scoreDown(height: Int, x: Int, y: Int, grid: List<List<Int>>): Int {
        return if(x==grid.size-1){
             0
        } else {
            val line = grid.map { it[y] }.takeLast(grid.size-x-1)
            val visible = line.takeWhile { it<height }.size
            visible+if(line.size>visible) 1 else 0
        }
    }
}