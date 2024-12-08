@file:Suppress("NAME_SHADOWING")

import kotlin.math.abs

fun main() {
    fun getPositions(input: List<String>): MutableMap<Char, MutableList<String>> {
        val positions: MutableMap<Char, MutableList<String>> = mutableMapOf()
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, char ->
                if (char != '.') {
                    val current = positions.getOrPut(char) { mutableListOf() }
                    current.add("$i,$j")
                    positions[char] = current
                }
            }
        }
        return positions
    }

    fun part1(input: List<String>): Int {
        val rowSize = input[0].lastIndex
        val columnSize = input.lastIndex
        // result is set of strings. I will save "x,y" values to see the unique ones
        val result: MutableSet<String> = mutableSetOf()
        val input = input.toMutableList()
        val positions = getPositions(input)
        positions.forEach { (_, u) ->
            // u is the list of items
            // a double for loop for the all the positions with all positions
            u.forEach { position ->
                u.forEach { position2 ->
                    // if the positions aren't equal
                    if (!position.equals(position2)) {
                        // get the point1 and point2
                        val (x1, y1) = position.split(',').map { it.toInt() }
                        val (x2, y2) = position2.split(',').map { it.toInt() }
                        // get the abs diff
                        val absx = abs(x1 - x2)
                        val absy = abs(y1 - y2)
                        // start with 4 coords
                        val rxa: Int
                        val rya: Int
                        val rxb: Int
                        val ryb: Int
                        // get the new 2 points values
                        if (x1 > x2) {
                            rxa = x1 + absx
                            rxb = x2 - absx
                        } else {
                            rxa = x1 - absx
                            rxb = x2 + absx
                        }
                        if (y1 > y2) {
                            rya = y1 + absy
                            ryb = y2 - absy
                        } else {
                            rya = y1 - absy
                            ryb = y2 + absy
                        }
                        if (rxa <= rowSize && rya <= columnSize && rxa >= 0 && rya >= 0) {
                            // add the value on the result
                            result.add("$rxa,$rya")
                        }
                        if (rxb <= rowSize && ryb <= columnSize && rxb >= 0 && ryb >= 0) {
                            // add the value on the result
                            result.add("$rxb,$ryb")
                        }
                    }
                }
            }
        }
        return result.count()
    }


    fun part2(input: List<String>): Int {
        val rowSize = input[0].lastIndex
        val columnSize = input.lastIndex
        // We use set to save the "x,y" values to remove duplicates
        val result: MutableSet<String> = mutableSetOf()
        val input = input.toMutableList()
        val positions = getPositions(input)
        positions.forEach { (_, u) ->
            // if the u is > 1 (no reason to do this but it was on the problem)
            if (u.size > 1) {
                // double for loop for the points on list
                u.forEach { position ->
                    u.forEach { position2 ->
                        // if the positions aren't the same
                        if (!position.equals(position2)) {
                            // get the point1, point2
                            val (x1, y1) = position.split(',').map { it.toInt() }
                            val (x2, y2) = position2.split(',').map { it.toInt() }
                            // add the points to result
                            result.add("$x1,$y1")
                            result.add("$x2,$y2")
                            // get abs distance
                            val absx = abs(x1 - x2)
                            val absy = abs(y1 - y2)

                            var rxa: Int
                            var rya: Int
                            var rxb: Int
                            var ryb: Int

                            // yet same thing just use the points to get the other 2 points
                            if (x1 > x2) {
                                rxa = x1 + absx
                                rxb = x2 - absx
                            } else {
                                rxa = x1 - absx
                                rxb = x2 + absx
                            }
                            if (y1 > y2) {
                                rya = y1 + absy
                                ryb = y2 - absy
                            } else {
                                rya = y1 - absy
                                ryb = y2 + absy
                            }

                            if (rxa <= rowSize && rya <= columnSize && rxa >= 0 && rya >= 0) {
                                // add result
                                result.add("$rxa,$rya")
                            }
                            if (rxb <= rowSize && ryb <= columnSize && rxb >= 0 && ryb >= 0) {
                                // add result
                                result.add("$rxb,$ryb")
                            }
                            // from there get the other points re-cursively
                            while (true) {
                                // get rxa,rya and rxb,ryb points to get the next ones
                                if (x1 > x2) {
                                    rxa += absx
                                    rxb -= absx
                                } else {
                                    rxa -= absx
                                    rxb += absx
                                }
                                if (y1 > y2) {
                                    rya += absy
                                    ryb -= absy
                                } else {
                                    rya -= absy
                                    ryb += absy
                                }
                                var yesBreak = false
                                if (rxa <= rowSize && rya <= columnSize && rxa >= 0 && rya >= 0) {
                                    // add result
                                    result.add("$rxa,$rya")
                                } else {
                                    yesBreak = true
                                }
                                if (rxb <= rowSize && ryb <= columnSize && rxb >= 0 && ryb >= 0) {
                                    // add result
                                    result.add("$rxb,$ryb")
                                } else {
                                    if (yesBreak) {
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result.count()
    }

    val testInput = readInput("Day8_test")
    check(part1(testInput) == 14)

    val testInputb = readInput("Day8_test")
    check(part2(testInputb) == 34)

    val input = readInput("Day8")

    part1(input).println()
    part2(input).println()
}
