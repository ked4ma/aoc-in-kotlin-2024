fun main() {
    fun part1(input: List<String>): Int {
        val H = input.size
        val W = input[0].length
        val groups = buildList {
            for (h in 0 until H) {
                for (w in 0 until W) {
                    if (input[h][w] != '.') add(Triple(input[h][w], h, w))
                }
            }
        }.groupBy(keySelector = { (c, _, _) -> c }, valueTransform = { (_, a, b) -> a to b })
        val antinodes = Array(H) { BooleanArray(W) { false } }
        for ((_, l) in groups) {
            for (i in 0 until l.size) {
                val (h1, w1) = l[i]
                for (j in i + 1 until l.size) {
                    val (h2, w2) = l[j]
                    val dh = h2 - h1
                    val dw = w2 - w1
                    if (h2 + dh in 0 until H && w2 + dw in 0 until W) antinodes[h2 + dh][w2 + dw] = true
                    if (h1 - dh in 0 until H && w1 - dw in 0 until W) antinodes[h1 - dh][w1 - dw] = true
                }
            }
        }
        return antinodes.sumOf { row -> row.count { it } }
    }

    fun part2(input: List<String>): Int {
        val H = input.size
        val W = input[0].length
        val groups = buildList {
            for (h in 0 until H) {
                for (w in 0 until W) {
                    if (input[h][w] != '.') add(Triple(input[h][w], h, w))
                }
            }
        }.groupBy(keySelector = { (c, _, _) -> c }, valueTransform = { (_, a, b) -> a to b })
        val antinodes = Array(H) { BooleanArray(W) { false } }
        for ((_, l) in groups) {
            for (i in 0 until l.size) {
                val (h1, w1) = l[i]
                for (j in i + 1 until l.size) {
                    val (h2, w2) = l[j]
                    antinodes[h1][w1] = true
                    antinodes[h2][w2] = true
                    val dh = h2 - h1
                    val dw = w2 - w1
                    var h = h2 + dh
                    var w = w2 + dw
                    while (h in 0 until H && w in 0 until W) {
                        antinodes[h][w] = true
                        h += dh
                        w += dw
                    }
                    h = h1 - dh
                    w = w1 - dw
                    while (h in 0 until H && w in 0 until W) {
                        antinodes[h][w] = true
                        h -= dh
                        w -= dw
                    }
                }
            }
        }
        return antinodes.sumOf { row -> row.count { it } }
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
