fun main() {
    var words: Map<String, Int> = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            sum += ((it.first { c -> c.isDigit() }.digitToInt() * 10) + it.last { c -> c.isDigit() }.digitToInt())
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            var firstDigit = 0;
            var lastDigit = 0;
            println(it)
            it.windowed(5, 1, true).forEach { window ->
                if (window.first().isDigit()) {
                    if (firstDigit == 0) {
                        firstDigit = window.first().digitToInt()
                    }
                }
                if (window.last().isDigit()) {
                    lastDigit = window.last().digitToInt()
                }
                words.keys.forEach { word ->
                    if (window.startsWith(word)) {
                        if (firstDigit == 0) {
                            firstDigit = words[word]!!
                        }
                    }
                    if (window.endsWith(word)) {
                        lastDigit = words[word]!!
                    }
                }
            }
            if (lastDigit == 0) {
                lastDigit = firstDigit
            }
            if (firstDigit == 0) {
                firstDigit = lastDigit
            }
            println(firstDigit * 10 + lastDigit)
            sum += firstDigit * 10 + lastDigit
        }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInputb = readInput("Day01_testb")
    check(part2(testInputb) == 281)
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")

    part1(input).println()
    println("------------")
    part2(input).println()
    Day01(input).solvePart2().println()
}

class Day01(private val input: List<String>) {

    private val words: Map<String, Int> = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun solvePart1(): Int =
        input.sumOf { calibrationValue(it) }

    fun solvePart2(): Int =
        input.sumOf { row ->
            calibrationValue(
                // Run through each character and turn it into a digit or a null,
                // and then map each of them to a String. In theory, we could take
                // the first and last digits from the resulting list instead of joining.
                row.mapIndexedNotNull { index, c ->
                    // If it is a digit, take it as-is
                    if (c.isDigit()) c
                    else
                    // Otherwise, see if this is the start of a word and if so map to the
                    // digit that it represents.
                        row.possibleWordsAt(index).firstNotNullOfOrNull { candidate ->
                            words[candidate]
                        }
                }.joinToString()
            )
        }

    private fun calibrationValue(row: String): Int =
        "${row.first { it.isDigit() }}${row.last { it.isDigit() }}".toInt()

    private fun String.possibleWordsAt(startingAt: Int): List<String> =
        (3..5).map { len ->
            substring(startingAt, (startingAt + len).coerceAtMost(length))
        }
}
