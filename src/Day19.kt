fun main() {
    fun countWays(towels: List<String>, pattern: String): Long {
        val dp = LongArray(pattern.length + 1)
        dp[0] = 1
        for (i in pattern.indices) {
            for (j in towels.indices) {
                val t = towels[j]
                if (t.length > i + 1) continue
                if (!t.indices.all { k -> t[k] == pattern[i - (t.length - 1) + k] }) continue
                dp[i + 1] += dp[i + 1 - t.length]
            }
        }
        return dp.last()
    }

    fun part1(input: List<String>): Int {
        val towels = input.first().split(", ")

        var ans = 0
        for (i in 2 until input.size) {
            if (countWays(towels, input[i]) > 0) ans++
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        val towels = input.first().split(", ")

        var ans = 0L
        for (i in 2 until input.size) {
            ans += countWays(towels, input[i])
        }

        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
