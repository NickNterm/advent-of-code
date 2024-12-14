fun main() {
    data class Robot(val x: Int, val y: Int, val velocityX: Int, val velocityY: Int)

    fun part1(input: List<String>): Int {
        val robots = mutableListOf<Robot>()
        input.forEach {
            val p = it.split(" ")[0].removeRange(0, 2)
            val v = it.split(" ")[1].removeRange(0, 2)

            robots.add(
                Robot(
                    p.split(",")[0].toInt(),
                    p.split(",")[1].toInt(),
                    v.split(",")[0].toInt(),
                    v.split(",")[1].toInt(),
                ),
            )
        }
        val movesToDo = 100
        // TODO change those to 7 and 11 to make work for the test
        val w = 101
        val h = 103
        val result: MutableList<Int> = mutableListOf()
        robots.forEachIndexed { index, robot ->
            var x = (robot.x + robot.velocityX * movesToDo) % w
            if (x < 0) {
                x += w
            }

            var y = (robot.y + robot.velocityY * movesToDo) % h
            if (y < 0) {
                y += h
            }
            robots[index] = robot.copy(
                x = x,
                y = y
            )
        }

        result.add(robots.filter { it.x < w / 2 && it.y < h / 2 }.size)
        result.add(robots.filter { it.x > w / 2 && it.y < h / 2 }.size)
        result.add(robots.filter { it.x < w / 2 && it.y > h / 2 }.size)
        result.add(robots.filter { it.x > w / 2 && it.y > h / 2 }.size)

        return result[0] * result[1] * result[2] * result[3]
    }

    fun printGrid(robots: List<Robot>) {
        for (i in 0..<103) {
            for (j in 0..<101) {
                val robot = robots.find { it.x == j && it.y == i }
                if (robot != null) {
                    print("x")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun part2(input: List<String>): Int {
        val robots = mutableListOf<Robot>()
        input.forEach {
            val p = it.split(" ")[0].removeRange(0, 2)
            val v = it.split(" ")[1].removeRange(0, 2)

            robots.add(
                Robot(
                    p.split(",")[0].toInt(),
                    p.split(",")[1].toInt(),
                    v.split(",")[0].toInt(),
                    v.split(",")[1].toInt(),
                ),
            )
        }
        val w = 101
        val h = 103

        repeat(10000) {
            robots.forEachIndexed { index, robot ->
                var x = (robot.x + robot.velocityX) % w
                if (x < 0) {
                    x += w
                }

                var y = (robot.y + robot.velocityY) % h
                if (y < 0) {
                    y += h
                }
                robots[index] = robot.copy(
                    x = x,
                    y = y
                )
            }
            // I saw a pattern starting at 195, and it was repeating for after 101 steps
            // I just sub 195 and mod with 101 to get the same pages
            // eventually I see that 7569 had the tree
            if ((it - 195) % 101 == 0) {
                println("TIMES ${it + 1}")
                printGrid(robots)
            }
            // This was the tree !!!
            // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            // x.............................x
            // x.............................x
            // x.............................x
            // x.............................x
            // x..............x..............x
            // x.............xxx.............x
            // x............xxxxx............x
            // x...........xxxxxxx...........x
            // x..........xxxxxxxxx..........x
            // x............xxxxx............x
            // x...........xxxxxxx...........x
            // x..........xxxxxxxxx..........x
            // x.........xxxxxxxxxxx.........x
            // x........xxxxxxxxxxxxx........x
            // x..........xxxxxxxxx..........x
            // x.........xxxxxxxxxxx.........x
            // x........xxxxxxxxxxxxx........x
            // x.......xxxxxxxxxxxxxxx.......x
            // x......xxxxxxxxxxxxxxxxx......x
            // x........xxxxxxxxxxxxx........x
            // x.......xxxxxxxxxxxxxxx.......x
            // x......xxxxxxxxxxxxxxxxx......x
            // x.....xxxxxxxxxxxxxxxxxxx.....x
            // x....xxxxxxxxxxxxxxxxxxxxx....x
            // x.............xxx.............x
            // x.............xxx.............x
            // x.............xxx.............x
            // x.............................x
            // x.............................x
            // x.............................x
            // x.............................x
            // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

        }

        return 0
    }

    //val testInput = readInput("Day14_test")
    // Change the todo on this code to make it work
    //check(part1(testInput) == 12)


    val input = readInput("Day14")

    part1(input).println()
    // uncomment to see the prints here
    part2(input).println()
}
