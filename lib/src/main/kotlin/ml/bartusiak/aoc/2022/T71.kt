package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.ceil



open class T71 : AOCTask {

    sealed interface File{
        fun size(): Long
        val name: String
        fun visit(function: (n: File) -> Unit)
        data class Directory(var files: MutableSet<File>, override val name: String): File{
            override fun size(): Long {
                return files.sumOf { it.size() }
            }
            override fun visit(function: (n: File) -> Unit) {
                function(this)
                files.forEach{it.visit(function)}
            }
        }
        data class FlatFile(val size: Long,override val name: String):  File{
            override fun size(): Long {
                return size
            }
            override fun visit(function: (n: File) -> Unit) {
                function(this)
            }
        }
    }

    fun parse(file: String ): File.Directory{
        val lines = data(file).drop(1)

        val state = ArrayDeque<File.Directory>()

        val root = File.Directory(mutableSetOf(), "")
        state.add(root)

        val acc = state

        lines.fold(acc){state, line ->
            val currentDir = state.last()
            if(line.equals("$ ls")){
                state
            } else if (line.startsWith("dir")){
                currentDir.files.add(File.Directory(mutableSetOf(), line.dropWhile { it !=' '}.drop(1)))
                state
            } else if (line[0].isDigit()){
                currentDir.files.add(File.FlatFile(line.split(" ")[0].toLong(), line.dropWhile { it !=' '}.drop(1)))
                state
            } else {
                require(line.startsWith("$ cd"))
                val to = line.replace("$ cd", "").trim()
                if(to == ".."){
                    state.removeLast()
                } else {
                    val newDir = state.last().files.find { it.name==to } as File.Directory
                    state.add(newDir)
                }
                state
            }
        }
        return root
    }
    open fun solve(file: String = "2022/T71.txt"): Long {
        val root = parse(file)
        var dirs = mutableSetOf<File.Directory>()
        root.visit { file ->
            when(file){
                is File.FlatFile -> {}
                is File.Directory -> {
                    if(file.size()<=100000){
                        dirs.add(file)
                    }
                }
            }
        }

        return  dirs.sumOf { it.size() }
    }
}