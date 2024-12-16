import java.util.*

fun main() {
    val dirs = listOf(
        -1 to 0, // ←
        0 to -1, // ↑
        1 to 0,  // →`
        0 to 1,  // ↓
    )

    data class Data(val x: Int, val y: Int, val c: Int, val d: Int)

    fun parseCheckPoint(input: List<CharArray>): List<Int> {
        val H = input.size
        val W = input.first().size
        var sx = 0
        var sy = 0
        var ex = 0
        var ey = 0
        for (i in 1 until H - 1) {
            for (j in 1 until W - 1) {
                when (input[i][j]) {
                    'S' -> {
                        sx = j
                        sy = i
                    }

                    'E' -> {
                        ex = j
                        ey = i
                    }
                }
            }
        }
        return listOf(sx, sy, ex, ey)
    }

    fun calc(input: List<CharArray>, sx: Int, sy: Int, sd: Int = 2): Pair<Array<IntArray>, Array<IntArray>> {
        val H = input.size
        val W = input.first().size
        val cost = Array(H) { IntArray(W) { Int.MAX_VALUE / 2 } }
        cost[sy][sx] = 0
        val dir = Array(H) { IntArray(W) { -1 } }
        dir[sy][sx] = sd
        val queue = PriorityQueue<Data>(compareBy { it.c })
        queue.offer(Data(sx, sy, 0, sd))
        while (queue.isNotEmpty()) {
            val (x, y, c, d) = queue.poll()
            if (c > cost[y][x]) continue
            for (dd in -1..1) {
                val nd = (d + dd + 4) % 4
                val (dx, dy) = dirs[nd]
                val nx = x + dx
                val ny = y + dy
                if (nx !in 1 until W - 1 || ny !in 1 until H - 1 || input[ny][nx] == '#') continue
                val nc = if (dd == 0) c + 1 else c + 1001
                if (nc < cost[ny][nx]) {
                    cost[ny][nx] = nc
                    dir[ny][nx] = nd
                    queue.offer(Data(nx, ny, nc, nd))
                }
            }
        }
        return cost to dir
    }

    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        val (sx, sy, ex, ey) = parseCheckPoint(map)
        val cost = calc(map, sx, sy).first
        return cost[ey][ex]
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        val H = map.size
        val W = map.first().size
        val (sx, sy, ex, ey) = parseCheckPoint(map)
        val (cost, dir) = calc(map, sx, sy)
        val targetCost = cost[ey][ex]
        var ans = 0
        for (x in 0 until W - 1) {
            for (y in 0 until H - 1) {
                val cost2 = calc(map, x, y, dir[y][x]).first
                if (cost[y][x] + cost2[ey][ex] == targetCost) ans++
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day16_test_1")
    check(part1(testInput) == 7036)
    check(part2(testInput) == 45)
    val testInput2 = readInput("Day16_test_2")
    check(part1(testInput2) == 11048)
    check(part2(testInput2) == 64)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
