import kotlin.math.log10

fun main() {
    fun solve(list: List<Long>, rep: Int): Long {
        var map = list.associateWith { 1L }
        repeat(rep) {
            map = buildMap {
                for ((n, c) in map) {
                    val len = log10(n.toDouble()).toInt() + 1
                    when {
                        n == 0L -> compute(1) { _, b -> (b ?: 0) + c }
                        len % 2 == 0 -> {
                            val m = 10L.pow(len / 2)
                            val l = n / m
                            val r = n % m
                            compute(l) { _, b -> (b ?: 0) + c }
                            compute(r) { _, b -> (b ?: 0) + c }
                        }

                        else -> compute(n * 2024) { _, b -> (b ?: 0) + c }
                    }
                }
            }
        }
        return map.values.fold(0L) { acc, v -> acc + v }
    }

    fun part1(input: List<String>): Long {
        val list = input[0].split(" ").map { it.toLong() }
        return solve(list, 25)
    }

    fun part2(input: List<String>): Long {
        val list = input[0].split(" ").map { it.toLong() }
        return solve(list, 75)
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("125 17")) == 55312L)

    // Or read a large test input from the `src/Day01_test.txt` file:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
