package ml.bartusiak.aoc.`2022`


open class T52 : T51() {


    override fun moveCrates(acc: List<ArrayDeque<Char>>, move: T51.Move): List<ArrayDeque<Char>> {
        val toMove = ArrayDeque<Char>()
        repeat(move.amount){
            toMove.add(0, acc[move.from-1].removeFirst())
        }
        toMove.forEach{e ->
            acc[move.to-1].add(0, e)
        }
        return acc
    }

}