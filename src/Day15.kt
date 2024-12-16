fun main() {
    fun part1(input: List<String>): Int {
        var moveLine = ""
        val map: MutableList<String> = mutableListOf()
        var currentPosition: Pair<Int, Int> = Pair(0, 0)
        input.forEachIndexed { index, it ->
            if (it.isNotEmpty() && it[0] == '#') {
                var line = it
                if (it.contains('@')) {
                    currentPosition = Pair(index, line.indexOf('@'))
                    line = line.replace('@', '.')
                }
                map.add(line)
            } else if (it.isNotEmpty() && it[0] != '#') {
                moveLine = it
            }
        }

        var numofzero = 0
        map.forEachIndexed { xindex, line ->
            line.forEachIndexed { yindex, char ->
                if (char == 'O') {
                    numofzero++
                }
            }
        }
        moveLine.forEachIndexed { index, char ->
            var xDir = 0
            var yDir = 0
            if (char == '^') {
                xDir = -1
            } else if (char == '<') {
                yDir = -1
            } else if (char == '>') {
                yDir = 1
            } else if (char == 'v') {
                xDir = 1
            }

            if (map[currentPosition.first + xDir][currentPosition.second + yDir] == '#') {
                // Do nothing this case
            } else if (map[currentPosition.first + xDir][currentPosition.second + yDir] == 'O') {
                if (xDir != 0) {
                    var x = currentPosition.first + xDir
                    var shouldMove = false
                    while (true) {
                        if (map[x][currentPosition.second] == '#') {
                            break
                        } else if (map[x][currentPosition.second] == 'O') {
                            x += xDir
                        } else if (map[x][currentPosition.second] == '.') {
                            shouldMove = true
                            break
                        }
                    }
                    if (shouldMove) {
                        map[x] = map[x].replaceRange(currentPosition.second, currentPosition.second + 1, "O")
                        x -= xDir
                        map[currentPosition.first + xDir] = map[currentPosition.first + xDir].replaceRange(
                            currentPosition.second,
                            currentPosition.second + 1,
                            "."
                        )
                        currentPosition = Pair(currentPosition.first + xDir, currentPosition.second)
                    }
                } else {
                    var y = currentPosition.second + yDir
                    var shouldMove = false
                    while (true) {
                        if (map[currentPosition.first][y] == '#') {
                            break
                        } else if (map[currentPosition.first][y] == 'O') {
                            y += yDir
                        } else if (map[currentPosition.first][y] == '.') {
                            shouldMove = true
                            break
                        }
                    }
                    if (shouldMove) {
                        map[currentPosition.first] = map[currentPosition.first].replaceRange(y, y + 1, "O")
                        map[currentPosition.first] = map[currentPosition.first].replaceRange(
                            currentPosition.second + yDir,
                            currentPosition.second + yDir + 1,
                            "."
                        )
                        currentPosition = Pair(currentPosition.first, currentPosition.second + yDir)
                    }
                }
            } else {
                currentPosition = Pair(currentPosition.first + xDir, currentPosition.second + yDir)
            }
        }

        var sum = 0
        numofzero = 0
        map.forEachIndexed { xindex, line ->
            line.forEachIndexed { yindex, char ->
                if (char == 'O') {
                    numofzero++
                    sum += 100 * xindex + yindex
                }
            }
        }
        return sum

    }

    fun part2(input: List<String>): Int {
        var moveLine = ""
        val map: MutableList<String> = mutableListOf()
        var currentPosition: Pair<Int, Int> = Pair(0, 0)
        input.forEachIndexed { index, it ->
            var line = it
            if (line.isNotEmpty() && line[0] == '#') {
                line = line.replace("#", "##")
                line = line.replace(".", "..")
                line = line.replace("@", "@.")
                line = line.replace("O", "[]")
                if (line.contains('@')) {
                    currentPosition = Pair(index, line.indexOf('@'))
                    line = line.replace('@', '.')
                }
                map.add(line)
            } else if (it.isNotEmpty() && it[0] != '#') {
                moveLine = line
            }
        }
        var sumofzeros = 0
        map.forEach { s ->
            s.forEach { c ->
                if (c == '[') {
                    sumofzeros++
                }
            }
        }

        moveLine.forEachIndexed { index, char ->
            var xDir = 0
            var yDir = 0
            if (char == '^') {
                xDir = -1
            } else if (char == '<') {
                yDir = -1
            } else if (char == '>') {
                yDir = 1
            } else if (char == 'v') {
                xDir = 1
            }

            sumofzeros = 0
            map.forEachIndexed { x, line ->
                line.forEachIndexed { y, c ->
                    if (c == '[') {
                        sumofzeros++
                    }
                    if (currentPosition.first == x && currentPosition.second == y) {
                        print("X")
                    } else {
                        print(c)
                    }
                }
                println()
            }

            if (map[currentPosition.first + xDir][currentPosition.second + yDir] == '#') {
                // Do nothing this case
            } else if (map[currentPosition.first + xDir][currentPosition.second + yDir] == '[' || map[currentPosition.first + xDir][currentPosition.second + yDir] == ']') {
                if (xDir != 0) {
                    var x = currentPosition.first + xDir
                    val c = map[x][currentPosition.second]
                    var shouldMove = false
                    val setToMove: MutableSet<Pair<Int, Int>> = mutableSetOf()
                    while (true) {
                        if (map[x][currentPosition.second] == '#') {
                            break
                        } else if (map[x][currentPosition.second] == '[' || map[x][currentPosition.second] == ']') {
                            if (map[x][currentPosition.second] == '[') {
                                setToMove.add(Pair(x, currentPosition.second))
                            } else if (map[x][currentPosition.second] == ']') {
                                setToMove.add(Pair(x, currentPosition.second - 1))
                            }
                            x += xDir
                            while (true) {
                                val toAdd: MutableSet<Pair<Int, Int>> = mutableSetOf()
                                setToMove.forEach {
                                    if (map[it.first + xDir][it.second] == '#' || map[it.first + xDir][it.second] == '#') {
                                        setToMove.clear()
                                        toAdd.clear()
                                        return@forEach
                                    }
                                    if (map[it.first + xDir][it.second] == '[') {
                                        toAdd.add(Pair(it.first + xDir, it.second))
                                    } else if (map[it.first + xDir][it.second] == ']') {
                                        toAdd.add(Pair(it.first + xDir, it.second - 1))
                                    } else if (map[it.first + xDir][it.second + 1] == '[') {
                                        toAdd.add(Pair(it.first + xDir, it.second + 1))
                                    } else if (map[it.first + xDir][it.second + 1] == ']') {
                                        toAdd.add(Pair(it.first + xDir, it.second))
                                    }
                                }
                                if (toAdd.isNotEmpty() && !setToMove.containsAll(toAdd)) {
                                    setToMove.addAll(toAdd)
                                } else {
                                    break
                                }
                                if (setToMove.isEmpty()) {
                                    break
                                }
                            }
                            break
                        } else if (map[x][currentPosition.second] == '.') {
                            shouldMove = true
                            setToMove.forEach {
                                if (map[it.first + xDir][it.second] == '#' || map[it.first + xDir][it.second + 1] == '#') {
                                    shouldMove = false
                                }
                            }
                            break
                        } else {
                            break
                        }
                    }
                    if (shouldMove) {
                        setToMove.reversed().forEach {
                            map[it.first + xDir] = map[it.first + xDir].replaceRange(it.second, it.second + 2, "[]")
                            map[it.first] = map[it.first].replaceRange(it.second, it.second + 2, "..")
                        }
                        x -= xDir
                        if (c == '[') {
                            map[currentPosition.first + xDir] = map[currentPosition.first + xDir].replaceRange(
                                currentPosition.second,
                                currentPosition.second + 2,
                                ".."
                            )
                        } else {
                            map[currentPosition.first + xDir] = map[currentPosition.first + xDir].replaceRange(
                                currentPosition.second - 1,
                                currentPosition.second + 1,
                                ".."
                            )
                        }
                        currentPosition = Pair(currentPosition.first + xDir, currentPosition.second)

                    }
                } else {
                    var y = currentPosition.second + yDir
                    var shouldMove = false
                    while (true) {
                        if (map[currentPosition.first][y] == '#') {
                            break
                        } else if (map[currentPosition.first][y] == '[' || map[currentPosition.first][y] == ']') {
                            y += yDir
                        } else if (map[currentPosition.first][y] == '.') {
                            shouldMove = true
                            break
                        }
                    }
                    if (shouldMove) {
                        map[currentPosition.first] = map[currentPosition.first].removeRange(y, y + 1)
                        map[currentPosition.first] =
                            StringBuilder(map[currentPosition.first]).apply { insert(currentPosition.second, '.') }
                                .toString()
                        currentPosition = Pair(currentPosition.first, currentPosition.second + yDir)
                    }
                }
            } else {
                currentPosition = Pair(currentPosition.first + xDir, currentPosition.second + yDir)
            }
        }

        var sum = 0

        sumofzeros = 0
        map.forEachIndexed { xindex, line ->
            line.forEachIndexed { yindex, char ->
                if (char == '[') {
                    sumofzeros++
                    sum += 100 * xindex + yindex
                }
            }
        }
        return sum

    }

    val testInputb = readInput("Day15_testb")
    check(part1(testInputb) == 2028)
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10092)

    // val testInputc = readInput("Day15_testc")
    //check(part2(testInputc) == 9021)
    //check(part2(testInput) == 9021)

    val input = readInput("Day15")

    part1(input).println()
    //part2(input).println()
}
