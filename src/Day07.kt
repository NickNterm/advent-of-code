fun main() {
    fun isValueTrueWithOr(result: Long, nums: List<Long>): Boolean {
        // if the first value is > than the result just return false no way to fix this
        if (result < nums[0]) {
            return false
        }
        // if the size is 1 that means we found the solution
        if (nums.size == 1) {
            return result == nums[0]
        } else {
            // else just do the calculations
            // the multiplication
            val mulNums = nums.toMutableList()
            mulNums[0] = nums[0] * nums[1]
            mulNums.removeAt(1)
            // the  addition
            val addNums = nums.toMutableList()
            addNums[0] = nums[0] + nums[1]
            addNums.removeAt(1)
            // the or statement
            val orNums = nums.toMutableList()
            orNums[0] = "${nums[0]}${nums[1]}".toLong()
            orNums.removeAt(1)
            return isValueTrueWithOr(result, mulNums)
                    || isValueTrueWithOr(result, addNums)
                    || isValueTrueWithOr(result, orNums)
        }
    }

    fun isValueTrue(result: Long, nums: List<Long>): Boolean {
        // if the first value is > than the result just return false no way to fix this
        if (result < nums[0]) {
            return false
        }
        // if the size is 1 that means we found the solution
        if (nums.size == 1) {
            return result == nums[0]
        } else {
            // else just do the calculations
            // the multiplication
            val mulNums = nums.toMutableList()
            mulNums[0] = nums[0] * nums[1]
            mulNums.removeAt(1)
            // the  addition
            val addNums = nums.toMutableList()
            addNums[0] = nums[0] + nums[1]
            addNums.removeAt(1)
            return isValueTrueWithOr(result, mulNums)
                    || isValueTrueWithOr(result, addNums)
        }
    }

    fun part1(input: List<String>): Long {
        // total sum
        var sum: Long = 0
        // for all the lines
        input.forEach { line ->
            // get the result
            val result = line.split(':')[0].toLong()
            // get the nums
            val nums = line.split(':')[1]
                .split(" ")// split by ' '
                .map { it.trim() }// trim
                .filter { it.isNotEmpty() } // remove empty strings
                .map { it.toLong() }// to long
            // execute the algorithm
            if (isValueTrue(result, nums)) {
                // add the sum if this works
                sum += result
            }
        }
        return sum
    }


    fun part2(input: List<String>): Long {
        var sum: Long = 0
        input.forEach { line ->
            // get the result
            val result = line.split(':')[0].toLong()
            // get the nums
            val nums = line.split(':')[1]
                .split(" ")// split by ' '
                .map { it.trim() }// trim
                .filter { it.isNotEmpty() } // remove empty strings
                .map { it.toLong() }// to long
            // execute the algorithm
            if (isValueTrueWithOr(result, nums)) {
                // add the sum if this works
                sum += result
            }
        }
        return sum
    }

    val testInput = readInput("Day7_test")
    check(part1(testInput).toInt() == 3749)

    val testInputb = readInput("Day7_test")
    check(part2(testInputb).toInt() == 11387)

    val input = readInput("Day7")

    part1(input).println()
    part2(input).println()
}
