import java.util.*

fun main() {
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    fun fenchForPosition(row: Int, col: Int, input: List<String>): Int {
        val currChar = input[row][col]
        var fench = 0
        if (row == 0 || row == input.lastIndex) {
            fench++
        }
        if (row > 0 && input[row - 1][col] != currChar) {
            fench++
        }
        if (row < input.lastIndex && input[row + 1][col] != currChar) {
            fench++
        }
        if (col == 0 || col == input.lastIndex) {
            fench++
        }
        if (col > 0 && input[row][col - 1] != currChar) {
            fench++
        }
        if (col < input[0].lastIndex && input[row][col + 1] != currChar) {
            fench++
        }
        return fench
    }

    fun isValidPosition(row: Int, col: Int, input: List<String>, curr: Char): Boolean {
        if (row >= 0 && row < input.size && col >= 0 && col < input[0].length) {
            if (input[row][col] == curr) {
                return true
            }
        }
        return false
    }

    fun bfs(grid: List<String>, i: Int, j: Int): Int {
        val queue = LinkedList<Pair<Int, Int>>()
        var area = 0
        var fench = 0
        visited += Pair(i, j)
        queue.add(Pair(i, j))
        while (queue.isNotEmpty()) {
            area++
            val curr = queue.poll()
            val row = curr.first
            val col = curr.second
            fench += fenchForPosition(row, col, grid)
            if (isValidPosition(row, col - 1, grid, grid[row][col]) && !visited.contains(Pair(row, col - 1))) {
                visited += Pair(row, col - 1)
                queue.add(Pair(row, col - 1))
            }
            if (isValidPosition(row, col + 1, grid, grid[row][col]) && !visited.contains(Pair(row, col + 1))) {
                visited += Pair(row, col + 1)
                queue.add(Pair(row, col + 1))
            }
            if (isValidPosition(row - 1, col, grid, grid[row][col]) && !visited.contains(Pair(row - 1, col))) {
                visited += Pair(row - 1, col)
                queue.add(Pair(row - 1, col))
            }
            if (isValidPosition(row + 1, col, grid, grid[row][col]) && !visited.contains(Pair(row + 1, col))) {
                visited += Pair(row + 1, col)
                queue.add(Pair(row + 1, col))
            }
        }
        return area * fench
    }

    fun part1(input: List<String>): Int {
        visited.clear()
        var result = 0
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (!visited.contains(Pair(row, col))) {
                    result += bfs(input, row, col)
                }
            }
        }
        return result
    }

    fun fenchForPositionPart2(row: Int, col: Int, input: List<String>): MutableList<Triple<Int, Int, Int>> {
        val currChar = input[row][col]
        val fench: MutableList<Triple<Int, Int, Int>> = mutableListOf()
        if (row == 0) {
            fench.add(Triple(row, col, 0))
        }
        if (row == input.lastIndex) {
            fench.add(Triple(row, col, 2))
        }
        if (row > 0 && input[row - 1][col] != currChar) {
            fench.add(Triple(row, col, 0))
        }
        if (row < input.lastIndex && input[row + 1][col] != currChar) {
            fench.add(Triple(row, col, 2))
        }
        if (col == 0) {
            fench.add(Triple(row, col, 3))
        }
        if (col == input.lastIndex) {
            fench.add(Triple(row, col, 1))
        }
        if (col > 0 && input[row][col - 1] != currChar) {
            fench.add(Triple(row, col, 3))
        }
        if (col < input[0].lastIndex && input[row][col + 1] != currChar) {
            fench.add(Triple(row, col, 1))
        }
        return fench
    }


    fun bfsPart2(grid: List<String>, i: Int, j: Int): Int {
        // 1359783
        val queue = LinkedList<Pair<Int, Int>>()
        var area = 0
        val fench: MutableSet<Triple<Int, Int, Int>> = mutableSetOf()
        var fenchSum = 0
        visited += Pair(i, j)
        queue.add(Pair(i, j))
        while (queue.isNotEmpty()) {
            area++
            val curr = queue.poll()
            val row = curr.first
            val col = curr.second
            val myFench = fenchForPositionPart2(row, col, grid)
            fench.addAll(myFench)
            if (isValidPosition(row, col + 1, grid, grid[row][col]) && !visited.contains(Pair(row, col + 1))) {
                visited += Pair(row, col + 1)
                queue.add(Pair(row, col + 1))
            }
            if (isValidPosition(row - 1, col, grid, grid[row][col]) && !visited.contains(Pair(row - 1, col))) {
                visited += Pair(row - 1, col)
                queue.add(Pair(row - 1, col))
            }
            if (isValidPosition(row, col - 1, grid, grid[row][col]) && !visited.contains(Pair(row, col - 1))) {
                visited += Pair(row, col - 1)
                queue.add(Pair(row, col - 1))
            }
            if (isValidPosition(row + 1, col, grid, grid[row][col]) && !visited.contains(Pair(row + 1, col))) {
                visited += Pair(row + 1, col)
                queue.add(Pair(row + 1, col))
            }
        }
        val seenFench = mutableSetOf<Triple<Int, Int, Int>>()
        fench.sortedBy { it.first * grid[0].length + it.second }.forEach {
            val row = it.first
            val col = it.second
            if (it.third == 0) {
                if (!seenFench.contains(Triple(row, col - 1, 0)) && !seenFench.contains(Triple(row, col + 1, 0))) {
                    fenchSum++
                }
            }
            if (it.third == 1) {
                if (!seenFench.contains(Triple(row - 1, col, 1)) && !seenFench.contains(Triple(row + 1, col, 1))) {
                    fenchSum++
                }
            }
            if (it.third == 2) {
                if (!seenFench.contains(Triple(row, col - 1, 2)) && !seenFench.contains(Triple(row, col + 1, 2))) {
                    fenchSum++
                }
            }
            if (it.third == 3) {
                if (!seenFench.contains(Triple(row - 1, col, 3)) && !seenFench.contains(Triple(row + 1, col, 3))) {
                    fenchSum++
                }
            }
            seenFench.add(Triple(row, col, it.third))
        }
        return area * fenchSum
    }

    fun part2(input: List<String>): Int {
        // 806050 ^^^
        visited.clear()
        var result = 0
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (!visited.contains(Pair(row, col))) {
                    result += bfsPart2(input, row, col)
                }
            }
        }
        return result
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)


    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
