package ml.bartusiak.aoc.`2021`

import ml.bartusiak.aoc.AOCTask

data class ImageData(val data: Set<Pair<Int,Int>>,val paddingValue: Boolean){
    fun countPixels(): Long = data.size.toLong()

    val maxX = data.maxOf { it.first }
    val minX = data.minOf { it.first }
    val maxY = data.maxOf { it.second }
    val minY = data.minOf { it.second }

    private fun inPadding(pair: Pair<Int, Int>): Boolean {
        val (x,y)=pair
        return !(x>=minX&&x<=maxX&&y>=minY&&y<=maxY)
    }
    override fun toString(): String{
        return ((minY-imagePad)..maxY+imagePad).joinToString("\n") { y ->
            ((minX-imagePad)..maxX+imagePad).joinToString("") { x ->
                val point = Pair(x,y)
                val value = if(inPadding(point)){
                    paddingValue
                } else{
                    data.contains(point)
                }
                if(value) {
                    "#"
                } else {
                    "."
                }
            }
        }
    }
    fun isActive(point: Pair<Int, Int>): Boolean{
        return  if(inPadding(point)){
            paddingValue
        } else{
            data.contains(point)
        }
    }
}
data class ImagingData(val algo: List<Boolean>,val image: ImageData)
val kernel = listOf(Pair(-1,-1), Pair(0,-1), Pair(1,-1), Pair(-1,0),  Pair(0,0), Pair(1,0), Pair(-1,1), Pair(0,1), Pair(1,1))
val imagePad = 3
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first+other.first, this.second+other.second)
open class T201 : AOCTask {

    companion object {
        fun solve(file: String = "T20.txt", steps: Int = 2): Long = T201().solve(file, steps)
    }

    open fun solve(file: String, steps: Int): Long {
        val imagingData = loadData(file)
        val result = generateSequence(imagingData, this::enchance).drop(steps).first()
        return result.image.countPixels()
    }

    fun enchance(imagingData: ImagingData): ImagingData{
        val (algo, image) = imagingData
        val paddingValue = algo.get(if (image.isActive(Pair(0,0))) 511 else 0)
        val enchancedData: Set<Pair<Int, Int>> = ((image.minY-imagePad)..image.maxY+imagePad).flatMap { y ->
            ((image.minX-imagePad)..image.maxX+imagePad).flatMap { x ->
                val point = Pair(x, y)
                if(x==0 && y==2 || x==2 && y==0){
                    print("s")
                }
                val index = kernel.map { element ->  element+point}.map{image.isActive(it)}.map { if(it) 1 else 0 }.joinToString("").toInt(2)
                val newValue = algo.get(index)
                if(newValue){
                    listOf(point+Pair(3,3))
                } else {
                    listOf()
                }
            }
        }.toSet()
        val newData = ImageData(enchancedData, paddingValue)
        return ImagingData(imagingData.algo, newData)
    }

    fun loadData(file: String): ImagingData {
        val lines = data(file).map{it.split("").filterNot { it.isEmpty() }}
        val algo: List<Boolean> = lines.first().map{it == "#"}
        val imageData = lines.drop(2).flatMapIndexed { y, line ->
            line.flatMapIndexed {x, value ->
                if (value=="#") {
                    listOf(Pair(x+3,y+3))
                } else {
                    listOf()
                }
            }
        }.toSet()
        val data =  ImageData(imageData, false)
        return ImagingData(algo,data)
    }



}




