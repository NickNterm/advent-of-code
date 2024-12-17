import kotlin.math.pow

fun main() {
    var regA = 0
    var regB = 0
    var regC = 0
    var instructions: List<Int>
    var ip = 0
    val output: MutableList<Int> = mutableListOf()

    class Instruction(var opcode: Int, var combo: Int) {
        fun comboValue(): Int {
            if (combo in 0..3) {
                return combo
            } else if (combo == 4) {
                return regA
            } else if (combo == 5) {
                return regB
            } else if (combo == 6) {
                return regC
            }
            return 0
        }

        fun execute(): Int {
            if (opcode == 0) { // adv
                val numerator = regA
                val denominator = 2.toDouble().pow(this.comboValue())
                val result = (numerator / denominator).toInt()
                regA = result
                ip += 2
                return result
            } else if (opcode == 1) { // bxl
                // it is the literal combo value
                regB = regB xor combo
                ip += 2
                return regB
            } else if (opcode == 2) { // bst
                regB = comboValue() % 8
                ip += 2
            } else if (opcode == 3) { // bst
                if (regA == 0) {
                    // do Nothing
                    ip += 2
                } else {
                    ip = combo
                }
                return ip
            } else if (opcode == 4) { // bxc
                regB = regB xor regC
                ip += 2
                return regB
            } else if (opcode == 5) { // out
                val value = comboValue() % 8
                output.add(value)
                ip += 2
                return value
            } else if (opcode == 6) { // bdv
                val numerator = regA
                val denominator = 2.toDouble().pow(this.comboValue())
                val result = (numerator / denominator).toInt()
                regB = result
                ip += 2
                return result
            } else if (opcode == 7) { // cdv
                val numerator = regA
                val denominator = 2.toDouble().pow(this.comboValue())
                val result = (numerator / denominator).toInt()
                regC = result
                ip += 2
                return result
            }
            return 0
        }
    }

    fun part1(input: List<String>): String {
        // 7,4,2,0,5,0,5,3,7
        output.clear()
        regA = input[0].toInt()
        regB = input[1].toInt()
        regC = input[2].toInt()
        instructions = input[3].split(",").map { it.toInt() }
        ip = 0
        while (ip < instructions.size) {
            val instruction = Instruction(instructions[ip], instructions[ip + 1])
            instruction.execute()
        }
        return output.joinToString(",")
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")

    val input = readInput("Day17")
    part1(input).println()
}
