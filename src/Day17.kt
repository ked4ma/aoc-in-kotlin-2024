fun main() {
    val reg = "Register .: (\\d+)".toRegex()
    val reg2 = "Program: (.+)".toRegex()

    fun run(registers: List<Long>, p: List<Int>): List<Int> {
        var A = registers[0]
        var B = registers[1]
        var C = registers[2]
        fun getCombo(operand: Int) = when (operand) {
            0, 1, 2, 3 -> operand.toLong()
            4 -> A
            5 -> B
            6 -> C
            else -> throw RuntimeException("reserved operand")
        }

        val res = mutableListOf<Int>()
        var i = 0
        while (i < p.size - 1) {
            val opcode = p[i]
            val operand = p[i + 1]
            when (opcode) {
                0 -> {
                    A /= 2L.pow(getCombo(operand).toInt())
                }

                1 -> {
                    B = B xor operand.toLong()
                }

                2 -> {
                    B = getCombo(operand) % 8
                }

                3 -> {
                    if (A != 0L) {
                        i = operand
                        continue
                    }
                }

                4 -> {
                    B = B xor C
                }

                5 -> {
                    res.add((getCombo(operand) % 8).toInt())
                }

                6 -> {
                    B = A / 2L.pow(getCombo(operand).toInt())
                }

                7 -> {
                    C = A / 2L.pow(getCombo(operand).toInt())
                }
            }
            i += 2
        }

        return res
    }

    fun part1(input: List<String>): String {
        val A = reg.find(input[0])!!.groupValues[1].toLong()
        val B = reg.find(input[1])!!.groupValues[1].toLong()
        val C = reg.find(input[2])!!.groupValues[1].toLong()
        val P = reg2.find(input[4])!!.groupValues[1].split(",").map { it.toInt() }

        return run(listOf(A, B, C), P).joinToString(",")
    }

    fun part2(input: List<String>): Long {
        val b = reg.find(input[1])!!.groupValues[1].toLong()
        val c = reg.find(input[2])!!.groupValues[1].toLong()
        val P = reg2.find(input[4])!!.groupValues[1].split(",").map { it.toInt() }
        val possibilities = mutableMapOf(
            0 to (0L until 8L).toList()
        )
        for (i in 1 until P.size) {
            val list = mutableListOf<Long>()
            for (p in possibilities.getValue(i - 1)) {
                for (q in 0 until 8) {
                    val a = p * 8 + q
                    val res = run(listOf(a, b, c), P)
                    if (res == P.slice(P.size - res.size until P.size)) {
                        list.add(a)
                    }
                    if (res == P) {
                        return a
                    }
                }
            }
            possibilities[i] = list
        }
        return -1
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day17_test_1")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    val testInput2 = readInput("Day17_test_2")
    check(part2(testInput2) == 117440L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
