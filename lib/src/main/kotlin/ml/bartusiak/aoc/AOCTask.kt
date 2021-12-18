package ml.bartusiak.aoc

interface AOCTask {

    fun data(name: String): List<String> =
        this::class.java.classLoader.getResource(name).readText().trim().split("\n").map { it.trim() }


}