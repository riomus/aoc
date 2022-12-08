package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil



open class T81 : AOCTask {

    open fun solve(file: String = "2022/T81.txt"): Int {
        val grid = data(file).map { it.split("").filter { it.isNotEmpty() }.map { it.toInt() } }

        val visibility = toVisibility(grid)

        return visibility.flatten().count { it }
    }

    private fun toVisibility(grid: List<List<Int>>): List<List<Boolean>> {
        return grid.mapIndexed{x, row ->
            row.mapIndexed { y, height ->
                isVisible(height, x, y, grid)
            }
        }
    }

    private fun isVisible(height: Int, x: Int, y: Int, grid: List<List<Int>>): Boolean {
        return isVisibleLeft(height, x, y, grid)||isVisibleRight(height, x, y, grid)||
            isVisibleTop(height, x, y, grid)||isVisibleDown(height, x, y, grid)
    }

    private fun isVisibleLeft(height: Int, x: Int, y: Int, grid: List<List<Int>>): Boolean {
        return if(y==0){
             true
        } else {
            grid[x].take(y).all { it < height }
        }
    }
    private fun isVisibleRight(height: Int, x: Int, y: Int, grid: List<List<Int>>): Boolean {
        return if(y==grid.size-1){
             true
        } else {
            grid[x].takeLast(grid.size-y-1).all { it < height }
        }
    }
    private fun isVisibleTop(height: Int, x: Int, y: Int, grid: List<List<Int>>): Boolean {
        return if(x==0){
            true
        } else {
            grid.map { it[y] }.take(x).all { it<height }
        }
    }
    private fun isVisibleDown(height: Int, x: Int, y: Int, grid: List<List<Int>>): Boolean {
        return if(x==grid.size-1){
             true
        } else {
            grid.map { it[y] }.takeLast(grid.size-x-1).all { it<height }
        }
    }
}