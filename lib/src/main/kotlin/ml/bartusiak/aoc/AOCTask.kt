package ml.bartusiak.aoc

import java.nio.charset.Charset

interface AOCTask {

    fun data(name: String): List<String> =
        this::class.java.classLoader.getResource(name).readText().trim().split("\n").map{it.trim()}


}