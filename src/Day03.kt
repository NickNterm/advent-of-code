fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        var count = 0
        input.forEach {
            var str = it
            // mul(x,y) regex
            val reg = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
            // match regex with the line
            var match = reg.find(str)
            // while loop if the match is not empty.
            while (match != null) {
                count++
                // get the first match
                val matchStr: String = match.groupValues.get(0)
                // remove the "mul(" on the start. Split by , and get the first. This is the first Integer
                val firstInt: Int = matchStr.removePrefix("mul(").split(",")[0].toInt()
                // remove the ")" on the end. Split by , and get the second. This is the second Integer
                val secondInt: Int = matchStr.removeSuffix(")").split(",")[1].toInt()
                // result is firstInt * secondInt
                sum += firstInt * secondInt
                // remove from the start to this show
                str = str.removeRange(0, match.range.last)
                match = reg.find(str)
            }
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        var inline = input.joinToString()
        // regex for do
        val doReg = """do\(\)""".toRegex()
        // regex for don't
        val dontReg = """don't\(\)""".toRegex()

        // matches for do/don'ts
        var doMatch: MatchResult?
        var dontMatch: MatchResult?

        // pointers to the findings
        var doEnd: Int
        var dontStart: Int

        // while forever
        while (true) {
            // find the do/don't
            doMatch = doReg.find(inline)
            dontMatch = dontReg.find(inline)
            // if the dont match is empty we are done
            if (dontMatch == null) break

            doEnd = doMatch?.range?.last ?: Int.MAX_VALUE
            dontStart = dontMatch.range.first

            if (doEnd > dontStart) {
                val end = if (doEnd != Int.MAX_VALUE) doEnd + 1 else inline.length - 1
                inline = inline.removeRange(dontStart, end)
            }
            if (dontStart > doEnd) {
                inline = inline.removeRange(doEnd - 3, doEnd + 1)
            }
        }

        val in2: List<String> = listOf(inline)
        return part1(in2)
    }

    val testInput = readInput("Day3_test")
    check(part1(testInput) == 161)

    val testInputb = readInput("Day3_testb")
    check(part2(testInputb) == 48)

    val input = readInput("Day3")

    part1(input).println()
    part2(input).println()
}
