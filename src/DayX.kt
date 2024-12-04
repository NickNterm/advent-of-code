fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }


    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("DayX_test")
    check(part1(testInput) == 1)

    val testInputb = readInput("DayX_testb")
    check(part2(testInputb) == 1)

    val input = readInput("DayX")

    part1(input).println()
    part2(input).println()
}
