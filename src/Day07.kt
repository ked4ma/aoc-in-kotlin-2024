fun main() {
    fun part1(input: List<String>): Long {
        fun dfs(n: Long, l: List<Long>): Boolean {
            fun dfs(i: Int = 1, c: Long = l.first()): Boolean {
                if (i == l.size) return c == n
                return dfs(i + 1, c + l[i]) || dfs(i + 1, c * l[i])
            }
            return dfs()
        }

        var ans = 0L
        input.forEach { line ->
            val (n, list) = line.split(": ").let { (l, r) -> l.toLong() to r.split(" ").map { it.toLong() } }
            if (dfs(n, list)) ans += n
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        fun dfs(n: Long, l: List<Long>): Boolean {
            fun dfs(i: Int = 1, c: Long = l.first()): Boolean {
                if (i == l.size) return c == n
                return dfs(i + 1, c + l[i]) || dfs(i + 1, c * l[i]) || dfs(i + 1, "$c${l[i]}".toLong())
            }
            return dfs()
        }

        var ans = 0L
        input.forEach { line ->
            val (n, list) = line.split(": ").let { (l, r) -> l.toLong() to r.split(" ").map { it.toLong() } }
            if (dfs(n, list)) ans += n
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
