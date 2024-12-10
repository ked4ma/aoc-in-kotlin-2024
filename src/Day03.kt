fun main() {
    fun part1(input: List<String>): Int {
        val regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
        return input.sumOf { line ->
            regex.findAll(line).sumOf {
                it.groupValues.let { (_, b, c) -> b.toInt() * c.toInt() }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val regex = "do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
        var ans = 0
        var enabled = true
        input.forEach { line ->
            regex.findAll(line).forEach { match ->
                when (match.value) {
                    "do()" -> enabled = true
                    "don't()" -> enabled = false
                    else -> {
                        if (enabled) ans += match.groupValues.let { (_, b, c) -> b.toInt() * c.toInt() }
                    }
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")) == 161)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

    // Or read a large test input from the `src/Day01_test.txt` file:
    // val testInput = readInput("Day03_test")
    // check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
