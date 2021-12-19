package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor


data class Beacon(val x: Int, val y:Int, val z: Int){
    operator fun minus(other: Beacon): Beacon{
        return Beacon(x-other.x,y-other.y,z-other.z)
    }
    operator fun plus(other: Beacon): Beacon{
        return Beacon(x+other.x,y+other.y,z+other.z)
    }
    fun select(index:Int):Int {
        return when(index){
            0 -> x
            1 -> y
            else -> z
        }
    }
    fun transform(negation: Negation, permutation: Permutation): Beacon{
        return Beacon(negation[0]*select(permutation[0]), negation[1]*select(permutation[1]), negation[2]*select(permutation[2]))
    }
    fun manhatan(other: Beacon): Int {
        return abs(x-other.x)+abs(y-other.y)+abs(z-other.z)
    }

}
typealias Negation = Array<Int>
typealias Permutation = Array<Int>
val negations = arrayOf(-1,1)
val positions =arrayOf(0,1,2)
val possibleNegations: Sequence<Array<Int>> = negations.flatMap { x -> negations.flatMap { y -> negations.map { z -> arrayOf(x,y,z) } } }.asSequence()
val possiblePermutations: Sequence<Array<Int>> = positions.flatMap { x -> positions.flatMap { y -> positions.map { z -> arrayOf(x,y,z) } } }.filterNot { it.toSet().size<3 }.asSequence()
data class Scanner(val beacons: Sequence<Beacon>){

    fun beaconsInPossibleViews(): Sequence<Triple<Negation, Permutation, Beacon>>{
       return possibleNegations.flatMap { negation ->
            possiblePermutations.flatMap { permutation ->
                beacons.map { beacon ->
                    Triple(negation, permutation, beacon.transform(negation, permutation))
                }
            }
        }
    }
}
data class MatchedScanner(val beacons: HashSet<Beacon>, val center:Beacon)
data class MatchingStep(val matched: List<MatchedScanner>, val beacons: Set<Beacon>, val unmatched: List<Scanner>)
open class T191 : AOCTask {


    companion object {
        fun solve(file: String = "T19.txt", minimalCommon: Int = 12): Long = T191().solve(file, minimalCommon)
    }

    open fun solve(file: String, minimalCommon: Int): Long {
        val scanners = loadScanners(file)
        val singeBaseScanners =  toSingleBase(scanners, minimalCommon)
        return singeBaseScanners.flatMap { it.beacons }.toSet().size.toLong()
    }

    fun toSingleBase(scanners: List<Scanner>, minimalCommon: Int): List<MatchedScanner>{
        val zero = MatchedScanner(scanners.first().beacons.toHashSet(), Beacon(0,0,0))
        val rest = scanners.drop(1)
        return generateSequence(MatchingStep(listOf(zero), zero.beacons, rest)){doMatching(it, minimalCommon)}.dropWhile { it.unmatched.isNotEmpty() }.first().matched
    }

    fun doMatching(step: MatchingStep, minimalCommon: Int): MatchingStep {
        val (matched, beacons, stepUnmatched) = step
        val matchings: Sequence<Pair<Scanner, MatchedScanner?>> = stepUnmatched.asSequence().map { singleUnmatched ->
            match(beacons, singleUnmatched, minimalCommon)
        }
       val (newMatched, unmatched) = matchings.firstOrNull { it.second != null }?.let{ found ->
            Pair(found.second!!, stepUnmatched.filter { it !=found.first }.toList())
        }!!
        return MatchingStep(matched + newMatched, newMatched.beacons + beacons, unmatched )
    }

    operator fun Scanner.plus(other: Beacon): Scanner{
        return Scanner(beacons.map { it+other })
    }

    fun Scanner.transform(negation: Negation, permutation: Permutation): Scanner{
        return Scanner(beacons.map { it.transform(negation, permutation) })
    }

    private fun match(beacons: Set<Beacon>, singleUnmatched: Scanner, minimalCommon: Int): Pair<Scanner, MatchedScanner?> {
        val mapping = beacons.flatMap { possibleZero ->
            singleUnmatched.beaconsInPossibleViews().map { (negation, permutation, possibleMatch) ->
                Triple(negation, permutation, possibleZero-possibleMatch)
            }}.groupingBy { it }.eachCount().maxByOrNull { it.value }?.takeIf { it.value>=minimalCommon }?.let { it.key }
        return mapping?.let { (negation, permutation, mapping) ->
            val mapped = singleUnmatched.transform(negation, permutation)+mapping
            Pair(singleUnmatched, MatchedScanner(mapped.beacons.toHashSet(), mapping))
        }?: run {
            Pair(singleUnmatched, null)
        }
    }

    fun loadScanners(file:String): List<Scanner>{
        return generateSequence(Pair(data(file), listOf<Scanner>())){(data, scanners) ->
            Pair(data.dropWhile { it.isNotEmpty() }.drop(1), scanners + listOf(parseScanner(data.takeWhile { it.isNotEmpty() })))
        }.dropWhile { it.first.isNotEmpty() }.first().second
    }

    private fun parseScanner(data: List<String>): Scanner {
        return Scanner(data.drop(1).map{ line ->
            val extracted = line.split(",").map { it.toInt() }
            Beacon(extracted[0], extracted[1], extracted[2])}.asSequence())
    }
}




