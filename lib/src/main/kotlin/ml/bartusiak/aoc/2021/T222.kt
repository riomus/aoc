package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask
import org.checkerframework.dataflow.qual.Deterministic
import kotlin.math.max


data class Cuboid(val x: Range, val y: Range, val z: Range){
    fun intersection(other: Cuboid): Cuboid? {
        val xOverlap = x.overlap(other.x)
        val yOverlap = y.overlap(other.y)
        val zOverlap = z.overlap(other.z)
        val hasCommonPart =  xOverlap.to>=xOverlap.from && yOverlap.to>=yOverlap.from&& zOverlap.to>=zOverlap.from
        return if(hasCommonPart){
             Cuboid(xOverlap, yOverlap, zOverlap)
        } else {
            null
        }
    }

    val volume = x.length*y.length*z.length
 }
open class T222 : T221() {

    companion object {
        fun solve(file: String = "2021/T21.txt"): Long = T222().solve(file)
    }

    override fun solve(file: String): Long {
        val instructions = loadInstructions(file)
        val count = countVolume(instructions)
        return count
    }


    private fun countVolume(instructions: List<RebootStep>): kotlin.Long {
        val startState: Triple<Long, MutableList<Cuboid>, MutableList<Cuboid>> = Triple(0L, mutableListOf(), mutableListOf())
        val (endVolume, _, _) = instructions.foldIndexed(startState){indx, (volume, iterAdd, iterSubstract), instr ->
            val toAdd = mutableListOf<Cuboid>().apply { addAll(iterAdd) }
            val toSubstract = mutableListOf<Cuboid>().apply { addAll(iterSubstract) }
            val instructionCuboid = Cuboid(instr.x, instr.y, instr.z)
            val (volumeAdded, newToAdd) = if(instr.instruction=="on"){
                    toAdd.add(instructionCuboid)
                    Pair(volume+instructionCuboid.volume, toAdd)
                }  else {
                    Pair(volume, toAdd)
                }
            val (doubleAddedVolume, doubleAddedSubstract) = iterAdd.fold(Pair(volumeAdded, toSubstract)){(volumeAcc, toSubstractAcc), cube ->
                instructionCuboid.intersection(cube)?.let { intersection ->
                    toSubstractAcc.add(intersection)
                    Pair(volumeAcc-intersection.volume, toSubstractAcc)
                }?: run {
                    Pair(volumeAcc, toSubstractAcc)
                }
            }
            val (doubleSubstractedVolume, doubleSubstractAdd) = iterSubstract.fold(Pair(doubleAddedVolume, newToAdd)){(volumeAcc, toAddAcc), cube ->
                instructionCuboid.intersection(cube)?.let { intersection ->
                    toAddAcc.add(intersection)
                    Pair(volumeAcc+intersection.volume, toAddAcc)
                }?: run {
                    Pair(volumeAcc, toAddAcc)
                }
            }
            Triple(doubleSubstractedVolume, doubleSubstractAdd, doubleAddedSubstract)
        }
        return endVolume
    }

}




