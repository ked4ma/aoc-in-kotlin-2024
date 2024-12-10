import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.count { line ->
            val list = line.split(" ").map { it.toInt() }
                .windowed(2, 1)
                .map { (a, b) -> b - a }
            (list.all { it >= 0 } || list.all { it <= 0 }) && list.all { abs(it) in 1..3 }
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val list = line.split(" ").map { it.toInt() }.toMutableList()
            (0 until list.size).any { i ->
                val n = list[i]
                list.removeAt(i)
                val res = list.windowed(2, 1).map { (a, b) -> b - a }.let { l ->
                    (l.all { it >= 0 } || l.all { it <= 0 }) && l.all { abs(it) in 1..3 }
                }
                list.add(i, n)
                res
            }
        }
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
