import kotlin.math.abs

fun main() {
    // Function to check if a list is safe or not
    fun isThisListSafe(list: List<Int>): Boolean {
        // get the last element
        var previous = list[0]
        var isListSafe = true
        val isIncreasing = list[1] > list[0]
        // for each item on the list loop
        list.forEachIndexed { indexInner, i ->
            // if it's first we skip
            if (indexInner != 0) {
                // get the distance from the previous to the current
                val dist = abs(previous - i)
                // if it is increasing that means prev<i
                if (isIncreasing) {
                    if (previous > i) {
                        // if prev > i is not safe
                        isListSafe = false
                    }
                } else {
                    // else
                    if (previous < i) {
                        // if prev < i is not safe
                        isListSafe = false
                    }
                }
                // if dist is more than 3 or 0 we say it's not safe
                if (dist > 3 || dist == 0) {
                    isListSafe = false
                }
            }
            // set the previous to the current value before we go to next
            previous = i
        }
        return isListSafe
    }

    fun part1(input: List<String>): Int {
        // get the list of safes
        val safe: MutableList<Int> = emptyList<Int>().toMutableList()

        // loop through the input
        input.forEachIndexed { index, it ->
            // split with space to create the list
            val list = it.split(" ").map { it.toInt() }
            // if list is safe add to safeList
            if (isThisListSafe(list)) {
                safe.add(index)
            }
        }

        // Return the size of safeList
        return safe.size
    }


    @Suppress("LABEL_NAME_CLASH")
    fun part2(input: List<String>): Int {
        // list of safe lines
        val safe: MutableList<Int> = emptyList<Int>().toMutableList()

        // loop through the input
        input.forEachIndexed { index, it ->
            // split
            val list = it.split(" ").map { it.toInt() }
            // if the line is safe add it
            if (isThisListSafe(list)) {
                safe.add(index)
            } else {
                // else loop through elements
                list.forEachIndexed { indexInner, i ->
                    val mutList: MutableList<Int> = emptyList<Int>().toMutableList()
                    mutList.addAll(list)
                    // delete the 1st, then 2nd, then 3rd...
                    mutList.removeAt(indexInner)
                    if (isThisListSafe(mutList)) {
                        // add if they are safe
                        safe.add(index)
                        return@forEachIndexed
                    }
                }
            }
        }
        // return the safe but remove duplicates
        return safe.distinctBy { it }.size
    }

    val testInput = readInput("Day2_test")
    check(part1(testInput) == 2)

    val testInputb = readInput("Day2_test")
    check(part2(testInputb) == 4)

    val input = readInput("Day2")

    part1(input).println()
    part2(input).println()
}