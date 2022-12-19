package ml.bartusiak.aoc.`2022`

import ml.bartusiak.aoc.AOCTask
import java.lang.Math.floor
import java.lang.Math.min

open class T191 : AOCTask {


    data class Resources(val ore: Long, val clay: Long, val obsidian: Long, val geode: Long ){

        companion object{
            val ZERO = Resources(0L, 0L, 0L, 0L)
        }
        operator fun plus(other: Resources): Resources {
            return Resources(ore+other.ore, clay+other.clay, obsidian+other.obsidian, geode+other.geode)
        }

        fun minusOre(amount: Long): Resources{
            return  Resources(ore-amount, clay, obsidian, geode)
        }
        fun minusClay(amount: Long): Resources{
            return  Resources(ore, clay-amount, obsidian, geode)
        }
        fun minusObsidian(amount: Long): Resources{
            return  Resources(ore, clay, obsidian-amount, geode)
        }
    }
    data class Robots(val ore: Long, val clay: Long, val obsidian: Long, val geode: Long ){
        companion object{
            val START = Robots(1L, 0L, 0L, 0L)
        }
        fun mine():Resources{
            return Resources(ore, clay, obsidian, geode)
        }

        fun plusOre(amount: Long): Robots{
            return Robots(ore+amount, clay, obsidian, geode)
        }
        fun plusClay(amount: Long): Robots{
            return Robots(ore, clay+amount, obsidian, geode)
        }
        fun plusObsidian(amount: Long): Robots{
            return Robots(ore, clay, obsidian+amount, geode)
        }
        fun plusGeode(amount: Long): Robots{
            return Robots(ore, clay, obsidian, geode+amount)
        }
     }
    data class Node(val resources: Resources, val robots: Robots){
        companion object{
            val START = Node(Resources.ZERO, Robots.START)
        }
    }

    data class ObsidianRobotCost(val ore: Long, val clay: Long)
    data class GeodeRobotCost(val ore: Long, val obsidian: Long)
    data class BluePrint(val oreOreCost: Long, val clayOreCost: Long, val obsidianCost: ObsidianRobotCost, val geodeRobotCost: GeodeRobotCost){

        fun nextStates(rawNode: Node, futureSteps: MutableSet<Node>){
            val maxOreSpending = oreOreCost+clayOreCost+obsidianCost.ore+geodeRobotCost.ore
            val maxClaySpending = obsidianCost.clay
            val maxObsidianSpending = geodeRobotCost.obsidian

            val node = Node(Resources(min(rawNode.resources.ore, 2*maxOreSpending), min(rawNode.resources.clay, 2*maxClaySpending), min(rawNode.resources.obsidian, 2*maxObsidianSpending), rawNode.resources.geode),
                Robots(min(maxOreSpending, rawNode.robots.ore), min(maxClaySpending, rawNode.robots.clay),min(maxObsidianSpending, rawNode.robots.obsidian), rawNode.robots.geode))

            val mining = node.resources+node.robots.mine()

            //just mine
            futureSteps.add(Node(mining, node.robots))
            //always buy geode if can
            if(node.resources.ore>=geodeRobotCost.ore && node.resources.obsidian>=geodeRobotCost.obsidian){
                futureSteps.add(Node(mining.minusOre(geodeRobotCost.ore).minusObsidian(geodeRobotCost.obsidian), node.robots.plusGeode(1L)))
            }else{
                //buy ore if can
                if(node.resources.ore>=oreOreCost && node.robots.ore<maxOreSpending){
                    futureSteps.add(Node(mining.minusOre(oreOreCost), node.robots.plusOre(1L)))
                }
                //buy clay if can
                if(node.resources.ore>=clayOreCost && node.robots.clay<maxClaySpending){
                    futureSteps.add(Node(mining.minusOre(clayOreCost), node.robots.plusClay(1L)))
                }
                //buy obsidian if can
                if(node.resources.ore>=obsidianCost.ore && node.resources.clay>=obsidianCost.clay && node.robots.obsidian < maxObsidianSpending){
                    futureSteps.add(Node(mining.minusOre(obsidianCost.ore).minusClay(obsidianCost.clay), node.robots.plusObsidian(1L)))
                }


            }
        }

    }

    data class SimulationStep(val nodes: MutableSet<Node>, val seen: MutableSet<Node>, val time: Long)

    ///Blueprint 1:
    //  Each ore robot costs 4 ore.
    //  Each clay robot costs 2 ore.
    //  Each obsidian robot costs 3 ore and 14 clay.
    //  Each geode robot costs 2 ore and 7 obsidian.

    fun solve(blueprint: BluePrint, steps: Long ): Long{
        val afterTime = generateSequence(SimulationStep(mutableSetOf(Node.START), hashSetOf(), 1L)) { (nodes, seen, time) ->
            seen.addAll(nodes)
            val newNodes = hashSetOf<Node>()
            nodes.forEach{node ->
                blueprint.nextStates(node, newNodes)
            }
            newNodes.removeAll(seen)
            println(time)
            SimulationStep(newNodes, seen, time+1L)
        }.dropWhile { it.time<steps+1 }.take(1).first()
        val maxNode = afterTime.nodes.maxBy { it.resources.geode }
        return maxNode.resources.geode
    }
    open fun solve(file: String, steps: Long): Long{
        val blueprints = data(file).flatMap { it.split(". ", ": ") }.windowed(5, 5, true).map { lines ->
                val splited = lines.map { it.split(" ") }
                BluePrint(splited[1][4].toLong(), splited[2][4].toLong(), ObsidianRobotCost(splited[3][4].toLong(), splited[3][7].toLong()), GeodeRobotCost(splited[4][4].toLong(), splited[4][7].toLong()))
            }
        val maxObsidianForPrints = blueprints.mapIndexed { idx, blueprint ->
            solve(blueprint, steps )
        }
        return maxObsidianForPrints.mapIndexed{idx, obs -> (idx+1)*(obs)}.sum()
        }

    }

