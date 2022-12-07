package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil



open class T72 : T71() {

    override fun solve(file: String): Long {
        val root = parse(file)
        val toRemove = 30000000L-(70000000L - root.size())
        var dirs = mutableSetOf<File.Directory>()
        root.visit { file ->
            when(file){
                is File.FlatFile -> {}
                is File.Directory -> {
                    if(file.size()>=toRemove){
                        dirs.add(file)
                    }
                }
            }
        }

        return  dirs.minOf { it.size() }
    }
}