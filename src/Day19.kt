fun main() {
    val dp: MutableMap<String, Int> = mutableMapOf()
    fun isValidTowel(pattern: String, towels: List<String>): Int {
        if (dp.containsKey(pattern)) {
            return dp[pattern]!!
        }
        if (pattern.isEmpty()) {
            dp[pattern] = 1
            return 1
        }
        for (towel in towels) {
            if (pattern.startsWith(towel)) {
                if (isValidTowel(pattern.removePrefix(towel), towels) == 1) {
                    return 1
                }
            }
        }
        dp[pattern] = 0
        return 0
    }

    fun part1(input: List<String>): Int {
        val compList: List<String> = input[0].split(",").map { it.trim() }
        var sum = 0
        dp.clear()
        for (i in 2..input.lastIndex) {
            sum += isValidTowel(input[i], compList)
        }
        return sum
    }


    val perDp = mutableMapOf<String, Long>()
    fun permutationsOfTowel(pattern: String, towels: List<String>): Long {
        if (perDp.containsKey(pattern)) {
            return perDp[pattern]!!
        }
        var result = 0L
        if (pattern.isEmpty()) {
            perDp[pattern] = 1
            return 1
        }
        for (towel in towels) {
            if (pattern.startsWith(towel)) {
                result += permutationsOfTowel(pattern.removePrefix(towel), towels)
            }
        }
        perDp[pattern] = result
        return result
    }

    fun part2(input: List<String>): Long {
        val compList: List<String> = input[0].split(",").map { it.trim() }
        val validTowels: MutableList<String> = mutableListOf()
        dp.clear()
        perDp.clear()
        for (i in 2..input.lastIndex) {
            if (isValidTowel(input[i], compList) == 1) {
                validTowels.add(input[i])
            }
        }
        var ways = 0L
        for (i in validTowels.indices) {
            ways += permutationsOfTowel(validTowels[i], compList)
        }
        return ways
    }

    val testInput = readInput("Day19_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
