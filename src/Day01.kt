import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        // creating 2 lists that we will fill with the rows
        val rightList: MutableList<Int> = emptyList<Int>().toMutableList()
        val leftList: MutableList<Int> = emptyList<Int>().toMutableList()

        for (line in input) {
            if (line.isEmpty()) break
            // split the values
            val splited = line.split("   ").map { it.toInt() }
            // add the values to the lists
            rightList.add(splited[0])
            leftList.add(splited[1])
        }
        // Sort the lists. This is required cause then I am able to
        // check the items one by one. Hot stuff
        rightList.sort()
        leftList.sort()

        var sum = 0
        for (i in 0..rightList.lastIndex) {
            // getting the abs diff of the elements
            val dist = abs(leftList[i] - rightList[i])
            // add to the sum
            sum += dist
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        // create the same 2 lists to store the first values
        val rightList: MutableList<Int> = emptyList<Int>().toMutableList()
        val leftList: MutableList<Int> = emptyList<Int>().toMutableList()

        // also add a map to save the num of shows of each element
        val shows: MutableMap<Int, Int> = emptyMap<Int, Int>().toMutableMap()

        for (line in input) {
            if (line.isEmpty()) break
            // split the values
            val splited = line.split("   ").map { it.toInt() }
            // add the values to the lists
            rightList.add(splited[0])
            leftList.add(splited[1])
        }

        var sum = 0
        for (i in 0..rightList.lastIndex) {
            // get the current element from the right list
            val element: Int = rightList[i]
            // if the item is not on the map, that means we need to calculate the shows
            if (!shows.containsKey(element)) {
                // init the map to 0
                shows[element] = 0
                // for loop on the list and increment the counter
                leftList.forEach {
                    if (it == element) {
                        shows[element] = shows[element]!! + 1
                    }
                }
            }
            // final value is the shows * element
            val value: Int = shows[element]!! * element
            // add this to the sum
            sum += value
        }
        return sum
    }

    val testInput = readInput("Day1_test")
    check(part1(testInput) == 11)

    val testInputb = readInput("Day1_test")
    check(part2(testInputb) == 31)

    val input = readInput("Day1")

    part1(input).println()
    part2(input).println()
}