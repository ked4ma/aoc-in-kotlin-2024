fun main() {
    val dirs = listOf(
        1 to 0,
        -1 to 0,
        0 to 1,
        0 to -1,
    )

    fun dist(map: Array<BooleanArray>): Array<IntArray> {
        val h = map.size
        val w = map.first().size
        val dist = Array(h) { IntArray(w) { Int.MAX_VALUE / 2 } }
        dist[0][0] = 0
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(0 to 0)
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            for ((dx, dy) in dirs) {
                val nx = x + dx
                val ny = y + dy
                if (nx !in 0 until w || ny !in 0 until h || !map[ny][nx] || dist[ny][nx] < Int.MAX_VALUE / 2) continue
                dist[ny][nx] = dist[y][x] + 1
                queue.add(nx to ny)
            }
        }
        return dist
    }

    fun part1(input: List<String>, h: Int = 71, w: Int = 71, use: Int = Int.MAX_VALUE): Int {
        val map = Array(h) { BooleanArray(w) { true } }
        val bytes = input.take(use).map { it.split(",").map(String::toInt) }
        for ((x, y) in bytes) {
            map[y][x] = false
        }
        val dist = dist(map)
        return dist[h - 1][w - 1]
    }

    fun part2(input: List<String>, h: Int = 71, w: Int = 71): String {
        val map = Array(h) { BooleanArray(w) { true } }
        val bytes = input.map { it.split(",").map(String::toInt) }
        for (i in bytes.indices) {
            val (x, y) = bytes[i]
            map[y][x] = false
            val dist = dist(map)
            if (dist[h - 1][w - 1] >= Int.MAX_VALUE / 2) {
                return "$x,$y"
            }
        }
        throw RuntimeException("unexpected")
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day18_test")
    check(part1(testInput, 7, 7, 12) == 22)
    check(part2(testInput, 7, 7) == "6,1")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day18")
    part1(input, use = 1024).println()
    part2(input).println()
}
