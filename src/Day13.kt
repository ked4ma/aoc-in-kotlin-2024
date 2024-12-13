import kotlin.math.min

fun main() {
    val reg = "Button .: X\\+(\\d+), Y\\+(\\d+)".toRegex()
    val reg2 = "Prize: X=(\\d+), Y=(\\d+)".toRegex()

    fun parse(a: String, b: String, p: String): List<Long> {
        val (ax, ay) = reg.find(a)!!.groupValues.slice(1..2)
            .map { it.toLong() }.let { (x, y) -> x to y }
        val (bx, by) = reg.find(b)!!.groupValues.slice(1..2)
            .map { it.toLong() }.let { (x, y) -> x to y }
        val (px, py) = reg2.find(p)!!.groupValues.slice(1..2)
            .map { it.toLong() }.let { (x, y) -> x to y }
        return listOf(ax, ay, bx, by, px, py)
    }

    fun solve(ax: Long, ay: Long, bx: Long, by: Long, px: Long, py: Long): Long {
        // ax * a + bx * b = px
        // ay * a + by * b = py
        val c = ax * by - ay * bx
        val d = px * by - py * bx
        if (d % c == 0L) {
            val a = d / c
            val b = (px - ax * a) / bx
            if (ax * a + bx * b == px && ay * a + by * b == py) {
                return 3 * a + b
            }
        }
        return Long.MAX_VALUE
    }

    fun part1(input: List<String>): Int {
        var ans = 0
        var i = 0
        while (i < input.size) {
            val d = parse(input[i], input[i + 1], input[i + 2])
            val res = solve(d[0], d[1], d[2], d[3], d[4], d[5])
            if (res < Long.MAX_VALUE) {
                ans += res.toInt()
            }

            i += 4
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        var i = 0
        while (i < input.size) {
            val d = parse(input[i], input[i + 1], input[i + 2])
            val res = solve(d[0], d[1], d[2], d[3], d[4] + 10_000_000_000_000L, d[5] + 10_000_000_000_000L)
            if (res < Long.MAX_VALUE) {
                ans += res
            }

            i += 4
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
