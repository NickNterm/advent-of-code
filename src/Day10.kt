fun main() {
    fun dfs(
        row: Int,
        col: Int,
        singleResult: MutableMap<Pair<Int, Int>, Int>,
        result: MutableMap<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>,
        input: List<String>,
        currentPath: MutableList<Pair<Int, Int>>
    ) {
        currentPath.add(Pair(row, col))
        val value = input[row][col].digitToInt()
        if (value != 0) {
            if (row > 0 && input[row - 1][col].digitToInt() == value - 1) {
                dfs(row - 1, col, singleResult, result, input, currentPath)
                currentPath.removeLast()
            }
            if (row < input.lastIndex && input[row + 1][col].digitToInt() == value - 1) {
                dfs(row + 1, col, singleResult, result, input, currentPath)
                currentPath.removeLast()
            }
            if (col > 0 && input[row][col - 1].digitToInt() == value - 1) {
                dfs(row, col - 1, singleResult, result, input, currentPath)
                currentPath.removeLast()
            }
            if (col < input[0].lastIndex && input[row][col + 1].digitToInt() == value - 1) {
                dfs(row, col + 1, singleResult, result, input, currentPath)
                currentPath.removeLast()
            }
        } else {
            if (result.contains(currentPath.first())) {
                result[currentPath.first()]!!.add(Pair(row, col))
            }
            if (singleResult.contains(currentPath.first())) {
                singleResult[currentPath.first()] = singleResult[currentPath.first()]!! + 1

            }
        }
    }

    fun part1(input: List<String>): Int {
        val result: MutableMap<Pair<Int, Int>, MutableSet<Pair<Int, Int>>> =
            mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, value ->
                if (value == '9') {
                    result[Pair(row, col)] = mutableSetOf()
                    dfs(row, col, mutableMapOf(), result, input, mutableListOf())
                }
            }
        }
        var sum = 0
        result.forEach { sum += it.value.size }
        return sum
    }


    fun part2(input: List<String>): Int {
        val result: MutableMap<Pair<Int, Int>, Int> = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, value ->
                if (value == '9') {
                    result[Pair(row, col)] = 0
                    dfs(row, col, result, mutableMapOf(), input, mutableListOf())
                }
            }
        }
        var sum = 0
        result.forEach { sum += it.value }
        return sum
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)

    val testInputb = readInput("Day10_test")
    check(part2(testInputb) == 81)

    val input = readInput("Day10")

    part1(input).println()
    part2(input).println()
}
