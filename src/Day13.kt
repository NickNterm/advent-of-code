import java.math.BigDecimal
import java.math.RoundingMode

fun main() {


    fun part1(input: List<String>): Long {
        var lineIndex = 0
        var result = 0L
        repeat(input.size / 4 + 1) {
            val buttonA: Pair<BigDecimal, BigDecimal> =
                Pair(
                    input[lineIndex].split(" ")[0].toBigDecimal(),
                    input[lineIndex].split(" ")[1].toBigDecimal()
                )
            lineIndex++
            val buttonB: Pair<BigDecimal, BigDecimal> =
                Pair(
                    input[lineIndex].split(" ")[0].toBigDecimal(),
                    input[lineIndex].split(" ")[1].toBigDecimal()
                )
            lineIndex++
            val goal: Pair<BigDecimal, BigDecimal> =
                Pair(
                    input[lineIndex].split(" ")[0].toBigDecimal(),
                    input[lineIndex].split(" ")[1].toBigDecimal()
                )
            lineIndex += 2
            var cost = 0L
            val x =
                ((goal.first.multiply(buttonB.second)) - (buttonB.first * goal.second)).divide(
                    (buttonA.first * buttonB.second) - (buttonB.first * buttonA.second),
                    2,
                    RoundingMode.HALF_EVEN
                )
            val y =
                ((buttonA.first.multiply(goal.second)) - (goal.first * buttonA.second)).divide(
                    (buttonA.first * buttonB.second) - (buttonB.first * buttonA.second),
                    2,
                    RoundingMode.HALF_EVEN
                )
            if (x.remainder(BigDecimal.ONE).movePointRight(x.scale()).abs()
                    .toBigInteger() == 0.toBigInteger() && y.remainder(BigDecimal.ONE).movePointRight(y.scale()).abs()
                    .toBigInteger() == 0.toBigInteger()
            ) {
                cost = x.toLong() * 3 + y.toLong()
            }
            result += cost

        }
        return result
    }


    fun part2(input: List<String>): Long {
        var lineIndex = 0
        var result = 0L
        repeat(input.size / 4 + 1) {
            val buttonA: Pair<BigDecimal, BigDecimal> =
                Pair(
                    input[lineIndex].split(" ")[0].toBigDecimal(),
                    input[lineIndex].split(" ")[1].toBigDecimal()
                )
            lineIndex++
            val buttonB: Pair<BigDecimal, BigDecimal> =
                Pair(
                    input[lineIndex].split(" ")[0].toBigDecimal(),
                    input[lineIndex].split(" ")[1].toBigDecimal()
                )
            lineIndex++
            var goal: Pair<BigDecimal, BigDecimal> =
                Pair(
                    input[lineIndex].split(" ")[0].toBigDecimal(),
                    input[lineIndex].split(" ")[1].toBigDecimal()
                )
            lineIndex += 2
            goal = Pair(goal.first.add(10000000000000.toBigDecimal()), goal.second.add(10000000000000.toBigDecimal()))
            var cost = 0L
            val x =
                ((goal.first.multiply(buttonB.second)) - (buttonB.first * goal.second)).divide(
                    (buttonA.first * buttonB.second) - (buttonB.first * buttonA.second),
                    9,
                    RoundingMode.HALF_EVEN
                )
            val y =
                ((buttonA.first.multiply(goal.second)) - (goal.first * buttonA.second)).divide(
                    (buttonA.first * buttonB.second) - (buttonB.first * buttonA.second),
                    9,
                    RoundingMode.HALF_EVEN
                )
            if (x.remainder(BigDecimal.ONE).movePointRight(x.scale()).abs()
                    .toBigInteger() == 0.toBigInteger() && y.remainder(BigDecimal.ONE).movePointRight(y.scale()).abs()
                    .toBigInteger() == 0.toBigInteger()
            ) {
                cost = x.toLong() * 3 + y.toLong()
            }
            result += cost

        }
        return result
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480L)

    val testInputb = readInput("Day13_test")
    println("Part2 no check v")
    part2(testInputb).println()
    println("----------------")

    val input = readInput("Day13")

    part1(input).println()
    part2(input).println()
}
