fun main() {
    // not the best but a working solution.
    // you get the solution pretty fast without dp
    fun part1(input: List<String>): Int {
        var line: MutableList<Long>
        line = input[0].split(" ").map { it.toLong() }.toMutableList()
        repeat(25) {
            val newLine = mutableListOf<Long>()
            line.forEach {
                if (it == 0L) {
                    newLine.add(1)
                } else if (it.toString().length % 2 == 0) {
                    val intNum = it.toString()
                    val a = intNum.removeRange(intNum.length / 2, intNum.length).toLong()
                    val b = intNum.removeRange(0, intNum.length / 2).toLong()
                    newLine.add(a)
                    newLine.add(b)
                } else {
                    newLine.add(it * 2024)
                }
            }
            line = newLine
        }
        return line.size
    }

    val dp: MutableMap<Long, MutableList<Pair<Long, Int>>> = mutableMapOf()

    // basically the same code just check if you are able to find the solution on the dp table
    // if the result it is not on the table calculate it and then save it
    fun calcOneNum(num: Long, iterations: Int): Long {
        if (iterations == 75) {
            return 1
        }
        val newIterations = iterations + 1
        if (num == 0L) {
            if (dp[0] != null && dp[0]!!.find { it.second == iterations } != null) {
                return dp[0]!!.find { it.second == iterations }!!.first
            }
            val size = calcOneNum(1, newIterations)
            if (dp[0] == null) {
                dp[0] = mutableListOf()
            }
            dp[0]!!.add(Pair(size, iterations))
            return size
        } else if (num.toString().length % 2 == 0) {
            if (dp[num] != null && dp[num]!!.find { it.second == iterations } != null) {
                return dp[num]!!.find { it.second == iterations }!!.first
            }
            val intNum = num.toString()
            val a = intNum.removeRange(intNum.length / 2, intNum.length).toLong()
            val b = intNum.removeRange(0, intNum.length / 2).toLong()
            var size = calcOneNum(a, newIterations)
            size += calcOneNum(b, newIterations)
            if (dp[num] == null) {
                dp[num] = mutableListOf()
            }
            dp[num]!!.add(Pair(size, iterations))
            return size
        } else {
            if (dp[num] != null && dp[num]!!.find { it.second == iterations } != null) {
                return dp[num]!!.find { it.second == iterations }!!.first
            }
            val size = calcOneNum(num * 2024, newIterations)
            if (dp[num] == null) {
                dp[num] = mutableListOf()
            }
            dp[num]!!.add(Pair(size, iterations))
            return size
        }
    }


    // problem with part2 is that we get out of memory and its
    // really slow so we use dp to work our way to be faster
    fun part2(input: List<String>): Long {
        val line: MutableList<Long> = input[0].split(" ").map { it.toLong() }.toMutableList()
        var size = 0L
        line.forEach {
            val num = it
            size += calcOneNum(num, 0)
        }
        return size
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312)

    val testInputb = readInput("Day11_test")
    check(part2(testInputb) == 65601038650482)

    val input = readInput("Day11")

    part1(input).println()
    part2(input).println()
}
