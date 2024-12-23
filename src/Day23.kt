fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableMap<String, MutableList<String>> = mutableMapOf()
        input.forEach { line ->
            val (comp1, comp2) = line.split("-")
            if (!map.containsKey(comp1)) {
                map[comp1] = mutableListOf()
            }
            if (!map.containsKey(comp2)) {
                map[comp2] = mutableListOf()
            }
            map[comp1]!!.add(comp2)
            map[comp2]!!.add(comp1)
        }

        println(map.filter { it.key.startsWith("t") })
        val triples: MutableSet<String> = mutableSetOf()
        map.forEach { (key, value) ->
            value.forEach { item ->
                val relation = map[item]!!.filter { value.contains(it) }.toSet()
                if (relation.isNotEmpty()) {
                    relation.forEach {
                        if (value.contains(it)) {
                            val triple = mutableListOf(key, item, it)
                            triple.sort()
                            triples.add(triple.joinToString(","))
                        }
                    }
                }
            }
        }
        return triples.filter { it.split(",").map { it.startsWith("t") }.filter { it }.isNotEmpty() }.size
    }


//    fun part2(input: List<String>): String {
//        val map: MutableMap<String, MutableList<String>> = mutableMapOf()
//        input.forEach { line ->
//            val (comp1, comp2) = line.split("-")
//            if (!map.containsKey(comp1)) {
//                map[comp1] = mutableListOf()
//            }
//            if (!map.containsKey(comp2)) {
//                map[comp2] = mutableListOf()
//            }
//            map[comp1]!!.add(comp2)
//            map[comp2]!!.add(comp1)
//        }
//
//        val biggerNet = hasCycle(map)
//        println(biggerNet.toList().sorted().joinToString(","))
//        return biggerNet.toList().sorted().joinToString(",")
//    }

    val testInput = readInput("Day23_test")
    check(part1(testInput) == 7)

    //check(part2(testInput) == "co,de,ka,ta")

    val input = readInput("Day23")

    part1(input).println()
    //part2(input).println()
}
