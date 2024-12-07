fun main() {
    /* part 1 is a good enough solution. I have a problem with double for loop
     * problem is that I should have keep 3 variables. i,j,direction. and update those to do that algorithm. I don't.
     * so I have to change this code on the future I am not proud of it
     */
    fun part1(inputa: List<String>): Int {
        var sum = 1
        val input = inputa.toMutableList()
        while (true) {
            for (i in 0..input.lastIndex) {
                for (j in input[i].indices) {
                    if (input[i][j] == '^') {
                        // walk up
                        input[i] = input[i].replace('^', 'X')
                        for (k in i - 1 downTo -1) {
                            if (k == -1) return sum
                            if (input[k][j] != '#') {
                                if (input[k][j] != 'X') {
                                    sum++
                                    input[k] = input[k].replaceRange(j, j + 1, "X")
                                }
                            } else {
                                input[k + 1] = input[k + 1].replaceRange(j, j + 1, ">")
                                break
                            }
                        }
                        break
                    } else if (input[i][j] == 'v') {
                        // walk down
                        input[i] = input[i].replace('v', 'X')
                        for (k in i + 1..input.size) {
                            if (k == input.size) {
                                input[k - 1] = input[k - 1].replaceRange(j, j + 1, "X")
                                return sum
                            }
                            if (input[k][j] != '#') {
                                if (input[k][j] != 'X') {
                                    input[k] = input[k].replaceRange(j, j + 1, "X")
                                    sum++
                                }
                            } else {
                                input[k - 1] = input[k - 1].replaceRange(j, j + 1, "<")
                                break
                            }
                        }
                        break
                    } else if (input[i][j] == '>') {
                        // walk right
                        input[i] = input[i].replace('>', 'X')
                        for (k in j + 1..input[i].length) {
                            if (k == input[i].length) return sum
                            if (input[i][k] != '#') {
                                if (input[i][k] != 'X') {
                                    sum++
                                    input[i] = input[i].replaceRange(k, k + 1, "X")
                                }
                            } else {
                                input[i] = input[i].replaceRange(k - 1, k, "v")
                                break
                            }
                        }
                        break
                    } else if (input[i][j] == '<') {
                        // walk left
                        input[i] = input[i].replace('<', 'X')
                        for (k in j - 1 downTo -1) {
                            if (k == -1) return sum
                            if (input[i][k] != '#') {

                                if (input[i][k] != 'X') {
                                    sum++
                                    input[i] = input[i].replaceRange(k, k + 1, "X")
                                }
                            } else {
                                input[i] = input[i].replaceRange(k + 1, k + 2, "^")
                                break
                            }
                        }
                        break
                    }
                }
            }
        }
    }

    /*
     * Part 2 is even worse. Time here is on the moon. To get the correct solution algorithm run for 1:28:00 to get it
     * for loop after for loop after while tru after for loop after for loop.
     * of course I know this is bad code but it worked. I would keep the idea of the first part and had a set that stores
     * the i,j,direction pairs in memory and if i am on the same position as before then it's time to say we are on a loop
     * this code is the appropriate one which I didn't write. I will change that on the future
     */
    fun part2(inputa: List<String>): Int {
        var sum = 0
        var input: MutableList<String> = mutableListOf()
        input.addAll(inputa)
        val oldIn = input.toMutableList()
        var counter: Int
        val wasThere: MutableSet<String> = mutableSetOf()
        for (a in 0..input.lastIndex) {
            for (b in input[a].indices) {
                if (input[a][b] == '.') {
                    input[a] = input[a].replaceRange(b, b + 1, "#")
                    counter = 0
                    while (true) {
                        counter++
                        if (counter > 20000) {
                            if (counter < 90000) {
                                sum++
                            }
                            break
                        }
                        for (i in 0..input.lastIndex) {
                            for (j in input[i].indices) {
                                if (input[i][j] == '^') {
                                    wasThere.add(input[i])
                                    // walk up
                                    input[i] = input[i].replace('^', '.')
                                    for (k in i - 1 downTo -1) {
                                        if (k == -1) {
                                            counter = 999999
                                            break
                                        }
                                        if (input[k][j] == '#') {
                                            input[k + 1] = input[k + 1].replaceRange(j, j + 1, ">")
                                            break
                                        }
                                    }
                                    break
                                } else if (input[i][j] == 'v') {
                                    // walk down
                                    input[i] = input[i].replace('v', '.')
                                    for (k in i + 1..input.size) {
                                        if (k == input.size) {
                                            counter = 999999
                                            break
                                        }
                                        if (input[k][j] == '#') {
                                            input[k - 1] = input[k - 1].replaceRange(j, j + 1, "<")
                                            break
                                        }
                                    }
                                    break
                                } else if (input[i][j] == '>') {
                                    // walk right
                                    input[i] = input[i].replace('>', '.')
                                    for (k in j + 1..input[i].length) {
                                        if (k == input[i].length) {
                                            counter = 999999
                                            break
                                        }
                                        if (input[i][k] == '#') {
                                            input[i] = input[i].replaceRange(k - 1, k, "v")
                                            break
                                        }
                                    }
                                    break
                                } else if (input[i][j] == '<') {
                                    // walk left
                                    input[i] = input[i].replace('<', '.')
                                    for (k in j - 1 downTo -1) {
                                        if (k == -1) {
                                            counter = 999999
                                            break
                                        }
                                        if (input[i][k] == '#') {
                                            input[i] = input[i].replaceRange(k + 1, k + 2, "^")
                                            break
                                        }
                                    }
                                    break
                                }
                            }
                        }
                    }
                    input = oldIn.toMutableList()
                }
            }
        }
        return sum
    }


    val testInput = readInput("Day6_test")
    check(part1(testInput) == 41)

    val testInputb = readInput("Day6_test")
    check(part2(testInputb) == 6)

    val input = readInput("Day6")

    part1(input).println()
    part2(input).println()
}
