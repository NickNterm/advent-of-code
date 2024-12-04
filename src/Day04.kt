fun main() {
    fun part1(input: List<String>): Int {
        var xmasCounter = 0
        // many many many if loops to find the pattern
        input.forEachIndexed { lineIndex, curr ->
            curr.forEachIndexed { wordIndex, currChar ->
                if (currChar == 'X') {
                    if (lineIndex < input.size - 3) {
                        if (input[lineIndex + 1][wordIndex] == 'M' &&
                            input[lineIndex + 2][wordIndex] == 'A' &&
                            input[lineIndex + 3][wordIndex] == 'S'
                        ) {
                            xmasCounter++
                        }
                    }
                    if (lineIndex >= 3) {
                        if (input[lineIndex - 1][wordIndex] == 'M' &&
                            input[lineIndex - 2][wordIndex] == 'A' &&
                            input[lineIndex - 3][wordIndex] == 'S'
                        ) {
                            xmasCounter++
                        }
                    }
                    if (wordIndex >= 3) {
                        if (lineIndex >= 3) {
                            if (input[lineIndex - 1][wordIndex - 1] == 'M' &&
                                input[lineIndex - 2][wordIndex - 2] == 'A' &&
                                input[lineIndex - 3][wordIndex - 3] == 'S'
                            ) {
                                xmasCounter++
                            }
                        }
                        if (lineIndex < input.size - 3) {
                            if (input[lineIndex + 1][wordIndex - 1] == 'M' &&
                                input[lineIndex + 2][wordIndex - 2] == 'A' &&
                                input[lineIndex + 3][wordIndex - 3] == 'S'
                            ) {
                                xmasCounter++
                            }
                        }
                        if (input[lineIndex][wordIndex - 1] == 'M' &&
                            input[lineIndex][wordIndex - 2] == 'A' &&
                            input[lineIndex][wordIndex - 3] == 'S'
                        ) {
                            xmasCounter++
                        }
                    }
                    if (wordIndex < curr.length - 3) {
                        if (lineIndex < input.size - 3) {
                            if (input[lineIndex + 1][wordIndex + 1] == 'M' &&
                                input[lineIndex + 2][wordIndex + 2] == 'A' &&
                                input[lineIndex + 3][wordIndex + 3] == 'S'
                            ) {
                                xmasCounter++
                            }
                        }
                        if (lineIndex >= 3) {
                            if (input[lineIndex - 1][wordIndex + 1] == 'M' &&
                                input[lineIndex - 2][wordIndex + 2] == 'A' &&
                                input[lineIndex - 3][wordIndex + 3] == 'S'
                            ) {
                                xmasCounter++
                            }
                        }
                        if (input[lineIndex][wordIndex + 1] == 'M' &&
                            input[lineIndex][wordIndex + 2] == 'A' &&
                            input[lineIndex][wordIndex + 3] == 'S'
                        ) {
                            xmasCounter++
                        }
                    }
                }
            }
        }
        return xmasCounter
    }


    fun part2(input: List<String>): Int {
        var xmasCounter = 0
        // many many many if loops to find the pattern
        input.forEachIndexed { lineIndex, curr ->
            curr.forEachIndexed { wordIndex, currChar ->
                if (currChar == 'A') {
                    if (wordIndex >= 1 && lineIndex >= 1 && wordIndex < curr.length - 1 && lineIndex < input.size - 1) {
                        if (input[lineIndex - 1][wordIndex - 1] == 'M' &&
                            input[lineIndex + 1][wordIndex - 1] == 'M' &&
                            input[lineIndex - 1][wordIndex + 1] == 'S' &&
                            input[lineIndex + 1][wordIndex + 1] == 'S'
                        ) {
                            xmasCounter++
                        }
                        if (input[lineIndex - 1][wordIndex - 1] == 'S' &&
                            input[lineIndex + 1][wordIndex - 1] == 'M' &&
                            input[lineIndex - 1][wordIndex + 1] == 'S' &&
                            input[lineIndex + 1][wordIndex + 1] == 'M'
                        ) {
                            xmasCounter++
                        }
                        if (input[lineIndex - 1][wordIndex - 1] == 'M' &&
                            input[lineIndex + 1][wordIndex - 1] == 'S' &&
                            input[lineIndex - 1][wordIndex + 1] == 'M' &&
                            input[lineIndex + 1][wordIndex + 1] == 'S'
                        ) {
                            xmasCounter++
                        }
                        if (input[lineIndex - 1][wordIndex - 1] == 'S' &&
                            input[lineIndex + 1][wordIndex - 1] == 'S' &&
                            input[lineIndex - 1][wordIndex + 1] == 'M' &&
                            input[lineIndex + 1][wordIndex + 1] == 'M'
                        ) {
                            xmasCounter++
                        }
                    }
                }
            }
        }
        return xmasCounter
    }

    val testInput = readInput("Day4_test")
    check(part1(testInput) == 18)

    val testInputb = readInput("Day4_test")
    check(part2(testInputb) == 9)

    val input = readInput("Day4")

    part1(input).println()
    part2(input).println()
}
