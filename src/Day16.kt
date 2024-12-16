import java.util.*
import kotlin.math.abs

fun main() {
    fun dijkstra(input: List<String>, start: Pair<Int, Int>): Map<Pair<Int, Int>, Int> {
        val distances = mutableMapOf<Pair<Int, Int>, Int>().withDefault { Int.MAX_VALUE }
        val priorityQueue = PriorityQueue<List<Int>>(compareBy { it[3] }).apply {
            add(
                listOf(
                    start.first,
                    start.second,
                    1,
                    0
                ),
            )
        }

        distances[start] = 0

        while (priorityQueue.isNotEmpty()) {
            val (row, col, dir, currentDist) = priorityQueue.poll()
            val positions = listOf(Pair(row + 1, col), Pair(row, col + 1), Pair(row - 1, col), Pair(row, col - 1))
            positions.forEachIndexed { index, position ->
                if (input[position.first][position.second] == '.' || input[position.first][position.second] == 'E') {
                    var totalDist = Int.MAX_VALUE
                    if (index == dir) {
                        totalDist = currentDist + 1
                    } else if (abs(index - dir) != 2) {
                        totalDist = currentDist + 1001
                    }
                    if (totalDist < distances.getValue(position)) {
                        distances[position] = totalDist
                        priorityQueue.add(listOf(position.first, position.second, index, totalDist))
                    }
                }
            }
        }
        return distances
    }

    class Path constructor(var path: MutableList<Triple<Int, Int, Int>>, var cost: Int) {

        fun copy(): Path {
            val list = mutableListOf<Triple<Int, Int, Int>>()
            list.addAll(path)
            return Path(list, cost)
        }

        override fun toString() = "$cost|$path"
    }

    fun dijkstraWithPath(input: List<String>, start: Pair<Int, Int>): Int {
        val distances = mutableMapOf<Pair<Int, Int>, Int>().withDefault { Int.MAX_VALUE }
        val priorityQueue = PriorityQueue<Path>(compareBy { it.cost }).apply {
            add(
                Path(mutableListOf(Triple(start.first, start.second, 1)), 0)
            )
        }

        distances[start] = 0
        val seen = mutableSetOf<Pair<Int, Int>>()
        var firstCost = 0
        while (priorityQueue.isNotEmpty()) {
            val path = priorityQueue.poll()
            val row = path.path.last().first
            val col = path.path.last().second
            val dir = path.path.last().third
            val currentDist = path.cost
            val positions = listOf(Pair(row + 1, col), Pair(row, col + 1), Pair(row - 1, col), Pair(row, col - 1))
            positions.forEachIndexed { index, position ->
                if (input[position.first][position.second] == '.' || input[position.first][position.second] == 'E') {
                    var totalDist = Int.MAX_VALUE
                    if (index == dir) {
                        totalDist = currentDist + 1
                    } else if (abs(index - dir) != 2) {
                        totalDist = currentDist + 1001
                    }
                    if (input[position.first][position.second] == 'E') {
                        if (firstCost == 0) {
                            firstCost = totalDist
                        } else if (firstCost < totalDist) {
                            return seen.size
                        }
                    }
                    if (totalDist <= distances.getValue(position)) {
                        if (input[position.first][position.second] == 'E') {
                            path.path.forEach {
                                seen.add(Pair(it.first, it.second))
                            }
                            seen.add(Pair(position.first, position.second))

                        }
                        distances[position] = totalDist

                    }

                    val newPath = path.copy()
                    newPath.path.add(Triple(position.first, position.second, index))
                    newPath.cost = totalDist
                    priorityQueue.add(newPath)
                }
            }
        }
        return seen.size
    }


    fun part1(input: List<String>): Int {
        var startPos: Pair<Int, Int> = Pair(0, 0)
        var endPos: Pair<Int, Int> = Pair(0, 0)
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char == 'S') {
                    startPos = Pair(row, col)
                }
                if (char == 'E') {
                    endPos = Pair(row, col)
                }
            }
        }
        val result = dijkstra(input, startPos)
        return result[endPos]!!
    }


    fun part2(input: List<String>): Int {
        var startPos: Pair<Int, Int> = Pair(0, 0)
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char == 'S') {
                    startPos = Pair(row, col)
                }
            }
        }
        val result = dijkstraWithPath(input, startPos)
        return result
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 11048)

    val input = readInput("Day16")

    part1(input).println()
    val testInputb = readInput("Day16_testb")
    check(part2(testInput) == 64)
    check(part2(testInputb) == 45)

    // part2(input).println()
}
