fun main() {

    fun String.permute(result: String = ""): Set<String> =
        if (isEmpty()) setOf(result) else flatMapIndexed { i, c -> removeRange(i, i + 1).permute(result + c) }.toSet()

    fun String.smartPermute(): Set<String> {
        val keys: MutableMap<Char, Int> = mutableMapOf()
        for (char in toCharArray()) {
            if (keys.containsKey(char)) {
                keys[char] = keys[char]!! + 1
            } else {
                keys[char] = 1
            }
        }
        val charArray = toCharArray().distinct()
        val strs = charArray.joinToString("").permute()
        val set: MutableSet<String> = mutableSetOf()
        for (str in strs) {
            var building = ""
            val array = str.toCharArray()
            for (char in array) {
                for (i in 0..<keys[char]!!) {
                    building += char
                }
            }
            set.add(building)
        }
        return set
    }

    fun passesFromZero(moves: String, position: Pair<Int, Int>, zero: Pair<Int, Int>): Boolean {
        var calcPos = position
        moves.forEach { char ->
            if (char == '>') {
                calcPos = Pair(calcPos.first, calcPos.second + 1)
            }
            if (char == '<') {
                calcPos = Pair(calcPos.first, calcPos.second - 1)
            }
            if (char == '^') {
                calcPos = Pair(calcPos.first - 1, calcPos.second)
            }
            if (char == 'v') {
                calcPos = Pair(calcPos.first + 1, calcPos.second)
            }
            if (calcPos == zero) {
                return true
            }
        }
        return false
    }

    fun directionalMoveOrder(line: String): List<String> {
        val numericKeypad: List<List<Char>> =
            listOf(
                listOf(' ', '^', 'A'),
                listOf('<', 'v', '>'),
            )
        var currentPosition = Pair(0, 2) // Bottom right
        var results: MutableList<String> = mutableListOf()
        line.forEach { char ->
            val row = numericKeypad.indexOfFirst { it.contains(char) }
            val col = numericKeypad[row].indexOf(char)
            var result = ""
            if (row > currentPosition.first) {
                for (i in currentPosition.first..<row) {
                    result += "v"
                }
            }
            if (col < currentPosition.second) {
                for (i in col..<currentPosition.second) {
                    result += "<"
                }
            }
            if (col > currentPosition.second) {
                for (i in currentPosition.second..<col) {
                    result += ">"
                }
            }
            if (row < currentPosition.first) {
                for (i in row..<currentPosition.first) {
                    result += "^"
                }
            }
            if (results.isEmpty()) {
                for (p in result.smartPermute()) {
                    if (!passesFromZero(p, currentPosition, Pair(0, 0))) {
                        results.add(p + 'A')
                    }
                }
            } else {
                for (i in 0..<results.size) {
                    for (p in result.smartPermute()) {
                        if (!passesFromZero(p, currentPosition, Pair(0, 0))) {
                            results.add(results[i] + p + 'A')
                        }
                    }
                }
                results.removeIf { it.length == results[0].length }
                results = results.distinct().toMutableList()
            }
            currentPosition = Pair(row, col)
        }
        return results
    }


    fun numericMoveOrder(line: String): List<String> {
        val numericKeypad: List<List<Char>> =
            listOf(
                listOf('7', '8', '9'),
                listOf('4', '5', '6'),
                listOf('1', '2', '3'),
                listOf(' ', '0', 'A')
            )
        var currentPosition = Pair(3, 2) // Bottom right
        var results: MutableList<String> = mutableListOf()
        line.forEach { char ->
            val row = numericKeypad.indexOfFirst { it.contains(char) }
            val col = numericKeypad[row].indexOf(char)
            var result = ""
            if (row < currentPosition.first) {
                for (i in row..<currentPosition.first) {
                    result += "^"
                }
            }
            if (col < currentPosition.second) {
                for (i in col..<currentPosition.second) {
                    result += "<"
                }
            }
            if (col > currentPosition.second) {
                for (i in currentPosition.second..<col) {
                    result += ">"
                }
            }
            if (row > currentPosition.first) {
                for (i in currentPosition.first..<row) {
                    result += "v"
                }
            }
            if (results.isEmpty()) {
                for (p in result.smartPermute()) {
                    if (!passesFromZero(p, currentPosition, Pair(3, 0))) {
                        results.add(p + 'A')
                    }
                }
            } else {
                for (i in 0..<results.size) {
                    for (p in result.smartPermute()) {
                        if (!passesFromZero(p, currentPosition, Pair(3, 0))) {
                            results.add(results[i] + p + 'A')
                        }
                    }
                }
                results.removeIf { it.length == results[0].length }
                results = results.distinct().toMutableList()
            }
            currentPosition = Pair(row, col)
        }
        return results
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val finalMoveList: MutableList<String> = mutableListOf()
        input.forEach { line ->
            finalMoveList.clear()
            val value = line.removeSuffix("A").toInt()
            val moveOrder = numericMoveOrder(line)
            moveOrder.forEach { singleMoveOrder ->
                val robot1 = directionalMoveOrder(singleMoveOrder)
                robot1.forEach { move ->
                    val robot2 = directionalMoveOrder(move)
                    finalMoveList.addAll(robot2)
                }
            }
            finalMoveList.sortedBy { it.length }
            sum += finalMoveList.last().length * value
        }
        return sum
    }


    //fun part2(input: List<String>): Int {
    //return 0
    //}

    val testInput = readInput("Day21_test")
    check(part1(testInput) == 126384)

    //val testInputb = readInput("DayX_testb")
    //check(part2(testInputb) == 1)

    val input = readInput("Day21")

    part1(input).println()
    //part2(input).println()
}
