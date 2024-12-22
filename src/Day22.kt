fun main() {
    val digits: MutableMap<String, Int> = mutableMapOf()
    fun getTheGeneratedNumber(initialNumber: Long, depth: Int): Long {
        var secretNumber: Long = initialNumber
        var ones: Int = (secretNumber.toString().last()).digitToInt()
        val seenSeq: MutableSet<String> = mutableSetOf()

        val lastInstructions: MutableList<Int> = mutableListOf()
        var lastOnes = ones
        repeat(depth) {
            secretNumber = ((secretNumber shl 6) xor secretNumber) and 16777215 // mult 64
            secretNumber = ((secretNumber shr 5) xor secretNumber) and 16777215 // div 32
            secretNumber = ((secretNumber shl 11) xor secretNumber) and 16777215 // mult 2048

            ones = (secretNumber.toString().last()).digitToInt()
            lastInstructions.add(ones - lastOnes)
            if (lastInstructions.size == 5) {
                lastInstructions.removeFirst()
            }
            lastOnes = ones
            if (lastInstructions.size == 4) {
                val strSeq = lastInstructions.joinToString(",")
                if (!seenSeq.contains(strSeq)) {
                    seenSeq.add(strSeq)
                    if (!digits.containsKey(strSeq)) {
                        digits[strSeq] = ones
                    } else {
                        digits[strSeq] = digits[strSeq]!! + ones
                    }
                }
            }
        }
        return secretNumber
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEach { line ->
            sum += getTheGeneratedNumber(line.toLong(), 2000)
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        digits.clear()
        input.forEach { line ->
            getTheGeneratedNumber(line.toLong(), 2000)
        }
        var max = 0
        digits.forEach { number ->
            max = maxOf(max, number.value)
        }
        return max
    }

    val testInput = readInput("Day22_test")
    check(part1(testInput) == 37327623L)

    val testInputb = readInput("Day22_testb")
    check(part2(testInputb) == 23)

    val input = readInput("Day22")

    part1(input).println()
    part2(input).println()
}
