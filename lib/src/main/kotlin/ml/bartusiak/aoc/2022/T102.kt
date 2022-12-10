package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.max
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction2


open class T102 : T101() {


     fun solveCrt(file: String): String {
    val registersHistory = toHistory(file)
        var result = ""
      registersHistory.drop(1).forEachIndexed { ind, (before, during, after) ->
          if(ind%40==0){
              result += "\n"
          }
          result += if( (ind%40) in before.x-1 .. before.x+1){
              "#"
          } else {
              "."
          }

      }
        result +="\n"
        return result
    }

}