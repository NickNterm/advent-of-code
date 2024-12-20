import java.util.*
import kotlin.math.abs

fun main() {
    fun dijkstra(input: List<String>, start: Pair<Int, Int>): Map<Pair<Int, Int>, Int> {
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
                if (input[position.first][position.second] == '.' || input[position.first][position.second] == 'E') {
                    val totalDist = currentDist + 1
                    if (totalDist < distances.getValue(position)) {
                        distances[position] = totalDist
                        priorityQueue.add(listOf(position.first, position.second, totalDist))
                    }
                }
            }
        }
        return distances
    }

    fun part1(input: List<String>): Int {
        // input max = 93552
        var startPos = Pair(0, 0)
        var endPos = Pair(0, 0)
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (c == 'S') {
                    startPos = Pair(i, j)
                }
                if (c == 'E') {
                    endPos = Pair(i, j)
                }
            }
        }
        val dist = dijkstra(input, startPos)
        val cheats: MutableSet<String> = mutableSetOf()
        dist.forEach { distance ->
            dist.forEach { distance2 ->
                if (distance != distance2) {
                    //2578
                    if (abs(distance.value - distance2.value) >= 102) {
                        if (abs(distance.key.first - distance2.key.first) == 2 && distance2.key.second == distance.key.second) {
                            //println("Cheat for ${abs(distance.value - distance2.value) - 2}")
                            cheats.add("${distance.key.first},${distance.key.second}|${distance2.key.first},${distance2.key.second}")
                        }
                        if (abs(distance.key.second - distance2.key.second) == 2 && distance2.key.first == distance.key.first) {
                            //println("Cheat for ${abs(distance.value - distance2.value) - 2}")
                            cheats.add("${distance.key.first},${distance.key.second}|${distance2.key.first},${distance2.key.second}")
                        }
                    }
                }
            }
        }
        return dist[endPos]!!
    }

    fun dijkstraWallPath(input: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Map<Pair<Int, Int>, Int> {
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
                if (position.first >= 0 && position.second >= 0 && position.second <= input[0].lastIndex && position.first <= input.lastIndex) {
                    if (input[position.first][position.second] == '#' || input[position.first][position.second] == '.' || position == end || input[position.first][position.second] == 'E' || input[position.first][position.second] == 'S') {
                        val totalDist = currentDist + 1
                        if (totalDist < distances.getValue(position) && totalDist <= 20) {
                            distances[position] = totalDist
                            priorityQueue.add(listOf(position.first, position.second, totalDist))
                        }
                    }
                }
            }
        }
        return distances
    }

    fun part2(input: List<String>): Int {
        var startPos = Pair(0, 0)
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (c == 'S') {
                    startPos = Pair(i, j)
                }
            }
        }
        val dist = dijkstra(input, startPos)
        val cheats: MutableSet<String> = mutableSetOf()
        val mapSaves: MutableMap<Int, Int> = mutableMapOf()
        val checked: MutableSet<Pair<Int, Int>> = mutableSetOf()
        dist.forEach { distance ->
            checked.add(distance.key)
            dist.forEach { distance2 ->
                if (!checked.contains(distance2.key)) {
                    if (distance != distance2) {
                        if (abs(distance.value - distance2.value) > 98 && abs(distance.key.first - distance2.key.first) <= 20 && abs(
                                distance.key.second - distance2.key.second
                            ) <= 20
                        ) {
                            val walls = dijkstraWallPath(input, distance.key, distance2.key)
                            if (walls[distance2.key] != null) {
                                val result = walls[distance2.key]!!
                                if (result <= 20) {
                                    // 982399
                                    if (abs(distance.value - distance2.value) - result >= 100) {
                                        val save = abs(distance.value - distance2.value) - result
                                        if (mapSaves.containsKey(save)) {
                                            mapSaves[save] = mapSaves[save]!! + 1
                                        } else {
                                            mapSaves[save] = 1
                                        }
                                        cheats.add("${distance.key.first},${distance.key.second}|${distance2.key.first},${distance2.key.second}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        mapSaves.forEach {
            mapSaves[it.key] = it.value
        }
        return cheats.size
    }

    //val testInput = readInput("Day20_test")
    //check(part1(testInput) == 1)

    // val testInputb = readInput("DayX_testb")
    // check(part2(testInput) == 285)

    val input = readInput("Day20")

    part1(input).println()
    println("it will take a long time. Take a coffee. Around 5-6 mins")
    part2(input).println()
}
