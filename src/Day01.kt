import java.util.*
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val left = PriorityQueue<Long>()
        val right = PriorityQueue<Long>()
        for (i in input) {
            val (l, r) = i.split("   ").map { it.toLong() }
            left.offer(l)
            right.offer(r)
        }
        var ans = 0L
        repeat(input.size) {
            val l = left.poll()
            val r = right.poll()
            ans += abs(l - r)
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val left = mutableListOf<Long>()
        val right = mutableListOf<Long>()
        for (i in input) {
            val (l, r) = i.split("   ").map { it.toLong() }
            left.add(l)
            right.add(r)
        }
        val cnt = right.groupingBy { it }.eachCount()
        var ans = 0L
        for (l in left) {
            ans += l * cnt.getOrDefault(l, 0)
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 31L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
