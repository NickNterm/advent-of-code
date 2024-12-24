fun main() {
    data class Operation(val x: String, val op: String, val y: String, val z: String)

    fun execute(x: Boolean, y: Boolean, op: String): Boolean {
        when (op) {
            "AND" -> {
                return x && y
            }

            "OR" -> {
                return x || y
            }

            "XOR" -> {
                return (x && !y) || (y && !x)
            }
            // no way
            else -> return false
        }
    }

    fun part1(input: List<String>): Long {
        val wires: MutableMap<String, Boolean> = mutableMapOf()
        var writingWires = true
        val toBeCalculated: MutableList<Operation> = mutableListOf()
        input.forEach { line ->
            if (line.isEmpty()) {
                writingWires = false
            } else {
                if (writingWires) {
                    val (name, value) = line.split(": ")
                    wires[name] = value.toInt() == 1
                } else {
                    val (x, o, y, _, z) = line.split(" ")
                    if (wires.containsKey(x) && wires.containsKey(y)) {
                        wires[z] = execute(wires[x]!!, wires[y]!!, o)
                    } else {
                        toBeCalculated.add(Operation(x, y, o, z))
                    }
                }
            }
        }

        while (true) {
            if (toBeCalculated.isEmpty()) {
                break
            }
            val (x, y, o, z) = toBeCalculated[0]
            if (wires.containsKey(x) && wires.containsKey(y)) {
                wires[z] = execute(wires[x]!!, wires[y]!!, o)
                toBeCalculated.removeFirst()
            } else {
                toBeCalculated.removeFirst()
                toBeCalculated.add(Operation(x, y, o, z))
            }
        }

        val value =
            wires.filterKeys { it.startsWith("z") }.toSortedMap(compareBy<String> { it }).reversed()
                .map { if (it.value) 1 else 0 }
                .joinToString(separator = "").toLong(radix = 2)

        return value
    }


    val testInput = readInput("Day24_test")
    check(part1(testInput) == 2024L)


    val input = readInput("Day24")
    part1(input).println()
}
