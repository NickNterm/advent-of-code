fun main() {
    fun readConstraint(line: String, constraints: MutableMap<Int, MutableList<Int>>) {
        // get the pages on start
        // split the 2 pages
        val (a, b) = line.split("|").map { it.toInt() }
        val con = constraints[a]
        // if the constrainted page exists we just add the new item
        if (con != null) {
            con.add(b)
            constraints.replace(a, con)
        } else {
            //else we just add it on the list
            constraints.put(a, mutableListOf(b))
        }
    }

    fun part1(input: List<String>): Int {
        // this is a mode to check if you are reading pages or orders
        var readingPages = true
        // this is the constraints that exist on top
        val constraints: MutableMap<Int, MutableList<Int>> = emptyMap<Int, MutableList<Int>>().toMutableMap()
        // the final result
        var result = 0
        input.forEach { it ->
            if (it.isEmpty()) {
                // if the line is empty we change mode
                readingPages = false
            } else {
                // if you are reading pages
                if (readingPages) {
                    readConstraint(it, constraints)
                } else {
                    // split order by ,
                    val nums = it.split(",").map { it.toInt() }
                    var isSafe = true
                    // for each number
                    nums.forEachIndexed { index, num ->
                        // except the first
                        if (index > 0) {
                            // check if there are any constraints
                            val prevNums = constraints[num]
                            if (prevNums != null) {
                                // if there is then get the 0 to index-1
                                for (i in 0..index - 1) {
                                    // if there is one that means it is wrong
                                    if (prevNums.contains(nums[i])) {
                                        // say this is unsafe
                                        isSafe = false
                                    }
                                }
                            }
                        }
                    }
                    if (isSafe) {
                        // if on the end it's safe then get the middle
                        result += nums[nums.size / 2]
                    }
                }
            }
        }
        return result
    }


    fun part2(input: List<String>): Int {
        var readingPages = true
        val constraints: MutableMap<Int, MutableList<Int>> = emptyMap<Int, MutableList<Int>>().toMutableMap()
        var result = 0
        // same code as 1 but we switch the numbers when we have an error
        input.forEach { it ->
            if (it.isEmpty()) {
                readingPages = false
            } else {
                if (readingPages) {
                    readConstraint(it, constraints)
                } else {
                    val nums = it.split(",").map { it.toInt() }.toMutableList()
                    var isSafe = true
                    nums.forEachIndexed { index, num ->
                        if (index > 0) {
                            val prevNums = constraints[num]
                            if (prevNums != null) {
                                for (i in 0..<index) {
                                    if (prevNums.contains(nums[i])) {
                                        // switch the wrong numbers
                                        nums[i] += nums[index]
                                        nums[index] = nums[i] - nums[index]
                                        nums[i] = nums[i] - nums[index]
                                        // is safe false
                                        isSafe = false
                                    }
                                }
                            }
                        }
                    }
                    if (!isSafe) {
                        result += nums[nums.size / 2]
                    }
                }
            }
        }
        return result
    }

    val testInput = readInput("Day5_test")
    check(part1(testInput) == 143)

    val testInputb = readInput("Day5_test")
    check(part2(testInputb) == 123)

    val input = readInput("Day5")

    part1(input).println()
    println("Part 1 result ^^^")
    part2(input).println()
    println("Part 2 result ^^^")
}
