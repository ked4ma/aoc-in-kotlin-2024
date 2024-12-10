fun main() {
    fun part1(input: List<String>): Int {
        val H = input.size
        val W = input[0].length
        val dirs = listOf(
            intArrayOf(0, 1, 0, 2, 0, 3),       // →
            intArrayOf(1, 1, 2, 2, 3, 3),       // ↘
            intArrayOf(1, 0, 2, 0, 3, 0),       // ↓
            intArrayOf(1, -1, 2, -2, 3, -3),    // ↙
            intArrayOf(0, -1, 0, -2, 0, -3),    // ←
            intArrayOf(-1, -1, -2, -2, -3, -3), // ↖
            intArrayOf(-1, 0, -2, 0, -3, 0),    // ↑
            intArrayOf(-1, 1, -2, 2, -3, 3),    // ↗
        )
        var ans = 0
        for (h in 0 until H) {
            for (w in 0 until W) {
                if (input[h][w] != 'X') continue
                for ((dh1, dw1, dh2, dw2, dh3, dw3) in dirs) {
                    if (h + dh3 !in 0 until H || w + dw3 !in 0 until W) continue
                    if ("${input[h + dh1][w + dw1]}${input[h + dh2][w + dw2]}${input[h + dh3][w + dw3]}" == "MAS") {
                        ans++
                    }
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val H = input.size
        val W = input[0].length
        var ans = 0
        for (h in 1 until H - 1) {
            for (w in 1 until W - 1) {
                if (input[h][w] != 'A') continue
                if (listOf(input[h - 1][w - 1], input[h + 1][w + 1]).sorted().joinToString("") == "MS" &&
                    listOf(input[h - 1][w + 1], input[h + 1][w - 1]).sorted().joinToString("") == "MS"
                ) {
                    ans++
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private operator fun IntArray.component6(): Int {
    return get(5)
}
