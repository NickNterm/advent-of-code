import java.util.*

fun main() {
    fun dijkstra(input: List<List<Char>>, start: Pair<Int, Int>): Map<Pair<Int, Int>, Int> {
        val distances = mutableMapOf<Pair<Int, Int>, Int>().withDefault { Int.MAX_VALUE }
        val priorityQueue = PriorityQueue<List<Int>>(compareBy { it[2] }).apply {
            add(
                listOf(
                    start.first,
                    start.second,
                    0
                ),
            )
        }

        distances[start] = 0

        while (priorityQueue.isNotEmpty()) {
            val (row, col, currentDist) = priorityQueue.poll()
            val positions = listOf(Pair(row + 1, col), Pair(row, col + 1), Pair(row - 1, col), Pair(row, col - 1))
            positions.forEachIndexed { index, position ->
                if (position.first >= 0 && position.second >= 0 && position.first < input.size && position.second < input.size) {
                    if (input[position.first][position.second] == '.') {
                        val totalDist = currentDist + 1
                        if (totalDist < distances.getValue(position)) {
                            distances[position] = totalDist
                            priorityQueue.add(listOf(position.first, position.second, totalDist))
                        }
                    }
                }
            }
        }
        return distances
    }

    fun part1(input: List<String>): Int {
        val bytes = 1024

        val w = 70
        val h = 70
        val grid: MutableList<MutableList<Char>> =
            mutableListOf<MutableList<Char>>()
        val dotline = (0..w).map { '.' }
        for (i in 0..h) {
            grid.add(dotline.toMutableList())
        }
        for (i in 0..<bytes) {
            val (x, y) = input[i].split(",").map { it.toInt() }
            grid[y][x] = '#'
        }
        //grid.forEach {
        //    println(it)
        //}
        val result = dijkstra(grid, Pair(0, 0))
        return result[Pair(h, w)]!!
    }


    fun part2(input: List<String>, bytes: Int): Int {
        val w = 70
        val h = 70
        val grid: MutableList<MutableList<Char>> =
            mutableListOf<MutableList<Char>>()
        val dotline = (0..w).map { '.' }
        for (i in 0..h) {
            grid.add(dotline.toMutableList())
        }
        for (i in 0..<bytes) {
            val (x, y) = input[i].split(",").map { it.toInt() }
            grid[y][x] = '#'
        }
        val result = dijkstra(grid, Pair(0, 0))
        //println("bytes $bytes result ${result[Pair(h, w)]}")
        if (result[Pair(h, w)] == null) {
            return bytes
        }
        return result[Pair(h, w)]!!
    }

    //val testInput = readInput("Day18_test")
    //check(part1(testInput) == 22)

    //val testInputb = readInput("DayX_testb")
    //check(part2(testInputb) == 1)

    val input = readInput("Day18")

    part1(input).println()
    repeat(3000) {
        val result = part2(input, 1024 + it)
        if (result == 1024 + it) {
            println(result)
            return
        }
    }
}
