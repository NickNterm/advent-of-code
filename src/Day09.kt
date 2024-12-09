import java.math.BigInteger
import java.util.SortedMap

fun main() {
    /*
     * bad but functional way to solve part 1
     * use string, with ',' to split the ids
     * move them at the start like the algorithms shows
     */
    fun part1(input: List<String>): Long {
        // disk is the string
        var disk: String = input[0]
        // format with ','
        var format = ""
        // current id
        var id = 0
        disk.forEachIndexed { index, char ->
            if (index % 2 == 0) {
                // is Disk Space
                for (i in 1..char.digitToInt()) {
                    // add the number with a ','
                    format += "$id,"
                }
                id++
            } else {
                // is Free space
                for (i in 1..char.digitToInt()) {
                    // add the dot with a ','
                    format += ".,"
                }
            }
        }


        // This is a time-consuming task here to find the regex and replace everything. Not good
        format.forEachIndexed { index, char ->
            if (char != '.') {
                // if the value has just . on the end and 0-9 or , on start we are done
                if (!Regex("[0-9|,]+\\.?+").matches(format)) {
                    // split with ,
                    var splitted = format.split(",")
                    // remove a first dot
                    format = format.replaceFirst(".", splitted[splitted.lastIndex - 1])
                    // add the number there
                    format = format.replaceRange(
                        format.lastIndex - splitted.last().length - 1,
                        format.lastIndex + 1,
                        ""
                    )
                }
            }
        }

        // calc the checksum
        var checksum: BigInteger = 0.toBigInteger()
        format.split(",").filter { !it.isEmpty() }.forEachIndexed { index, it ->
            checksum += (it.toInt() * index).toBigInteger()
        }
        return checksum.toLong()
    }


    fun part2(input: List<String>): Long {
        var disk: String = input[0]
        var id = 0
        // now we save the parts on this map which is smooth
        val diskMap: SortedMap<Int, Pair<Int, Int>> = sortedMapOf()
        var position = 0
        disk.forEachIndexed { index, char ->
            if (index % 2 == 0) {
                // is Disk Space
                diskMap[position] = Pair(id, char.digitToInt())
                position += char.digitToInt()
                id++
            } else {
                // is Free space
                diskMap[position] = Pair(-1, char.digitToInt())
                position += char.digitToInt()
            }
        }

        // in case we have 2 or more adjacent empty spaces we make them one
        var key: Int? = diskMap.keys.first()
        while (true) {
            try {
                var nextKey = diskMap.keys.find { it > key!! }
                if (diskMap[key]!!.first == -1 && diskMap[nextKey]!!.first == -1) {
                    diskMap[key!!] = diskMap[key]!!.copy(second = diskMap[nextKey]!!.second + diskMap[key]!!.second)
                    diskMap.remove(nextKey)
                }
                key = diskMap.keys.find { it > key!! }
            } catch (e: Exception) {
                break
            }
        }

        val keys = diskMap.keys
        // get the last key
        var currentKey = keys.last()
        while (currentKey > 0) {
            // for all the keys we have
            for (key in keys) {
                // if it is an empty space with capacity more or equal to us and it's a smaller key we go there
                if (diskMap[key]!!.first == -1 && diskMap[key]!!.second >= diskMap[currentKey]!!.second && key < currentKey) {
                    var emptySpace = diskMap[key]!!.second
                    diskMap[key] = Pair(diskMap[currentKey]!!.first, diskMap[currentKey]!!.second)
                    // create the empty space that we leave behind
                    if (emptySpace - diskMap[currentKey]!!.second > 0) {
                        diskMap[key + diskMap[currentKey]!!.second] =
                            Pair(
                                -1,
                                emptySpace - diskMap[currentKey]!!.second
                            )
                    }
                    // also merge this space with any neighbor space
                    var prevKey: Int?
                    try {
                        prevKey = diskMap.keys.last { it < currentKey }
                    } catch (e: Exception) {
                        prevKey = null
                    }

                    var nextKey: Int?
                    try {
                        nextKey = diskMap.keys.find { it > currentKey }
                    } catch (e: Exception) {
                        nextKey = null
                    }
                    if (prevKey != null && nextKey != null && diskMap[prevKey]!!.first == -1 && diskMap[nextKey]!!.first == -1) {
                        diskMap[prevKey] =
                            diskMap[prevKey]!!.copy(second = diskMap[prevKey]!!.second + diskMap[nextKey]!!.second + diskMap[currentKey]!!.second)
                        diskMap.remove(nextKey)
                        diskMap.remove(currentKey)
                    } else if (prevKey != null && diskMap[prevKey]!!.first == -1) {
                        diskMap[prevKey] =
                            diskMap[prevKey]!!.copy(second = diskMap[prevKey]!!.second + diskMap[currentKey]!!.second)
                        diskMap.remove(currentKey)
                    } else if (nextKey != null && diskMap[nextKey]!!.first == -1) {
                        diskMap[currentKey] =
                            diskMap[currentKey]!!.copy(
                                first = -1,
                                second = diskMap[nextKey]!!.second + diskMap[currentKey]!!.second
                            )
                        diskMap.remove(nextKey)
                    } else {
                        diskMap[currentKey] = diskMap[currentKey]!!.copy(
                            first = -1,
                            second = diskMap[currentKey]!!.second
                        )
                    }
                    break
                }
            }
            // get the next current key
            currentKey = diskMap.keys.last { it < currentKey && diskMap[it]!!.first != -1 } ?: -1
        }

        // calculate the checksum
        var checksum: BigInteger = 0.toBigInteger()
        for (key in diskMap.keys) {
            if (diskMap[key]!!.first != -1) {
                for (i in 0..<diskMap[key]!!.second) {
                    checksum += ((key + i) * diskMap[key]!!.first).toBigInteger()
                }
            }
        }
        return checksum.toLong()
    }

    val testInput = readInput("Day9_test")
    part1(testInput).println()
    check(part1(testInput) == 1928.toLong())

    val testInputb = readInput("Day9_test")
    check(part2(testInputb) == 2858.toLong())

    val input = readInput("Day9")
    part1(input).println()
    part2(input).println()
}