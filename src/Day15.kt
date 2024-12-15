import kotlin.math.max
import kotlin.math.min

fun main() {
    fun dir(op: Char) = when (op) {
        '^' -> 0 to -1
        'v' -> 0 to 1
        '<' -> -1 to 0
        '>' -> 1 to 0
        else -> throw RuntimeException("unknown op")
    }

    fun part1(input: List<String>): Int {
        val splitIndex = input.indexOfFirst { it.isEmpty() }
        val map = input.slice(0 until splitIndex).map { it.toCharArray() }
        val H = map.size
        val W = map.first().size
        val ops = input.slice(splitIndex + 1 until input.size).joinToString("")

        var x = 0
        var y = 0
        for (i in 0 until H) {
            for (j in 0 until W) {
                if (map[i][j] == '@') {
                    x = j
                    y = i
                }
            }
        }

        for (op in ops) {
            var ex = x
            var ey = y
            val (dx, dy) = dir(op)
            var movable = false
            while (map[ey + dy][ex + dx] != '#') {
                ex += dx
                ey += dy
                if (map[ey][ex] == '.') {
                    movable = true
                    break
                }
            }
            while (true) {
                if (x == ex && y == ey) break
                if (map[ey][ex] == '.') {
                    map[ey][ex] = map[ey - dy][ex - dx]
                    map[ey - dy][ex - dx] = '.'
                }
                ex -= dx
                ey -= dy
            }
            if (movable) {
                x += dx
                y += dy
            }
            // "======".println()
            // op.println()
            // map.joinToString("\n") { it.concatToString() }.println()
            // map[y][x].println()
        }
        // ops.println()

        var ans = 0
        for (h in 0 until H) {
            for (w in 0 until W) {
                if (map[h][w] == 'O') ans += (h * 100) + w
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val splitIndex = input.indexOfFirst { it.isEmpty() }
        val map = input.slice(0 until splitIndex).map {
            it.map { c ->
                when (c) {
                    '.' -> ".."
                    '#' -> "##"
                    'O' -> "[]"
                    '@' -> "@."
                    else -> "unknown type"
                }
            }.joinToString("").toCharArray()
        }
        val H = map.size
        val W = map.first().size
        val ops = input.slice(splitIndex + 1 until input.size).joinToString("")

        // map.joinToString("\n") { it.concatToString() }.println()
        var x = 0
        var y = 0
        for (i in 0 until H) {
            for (j in 0 until W) {
                if (map[i][j] == '@') {
                    x = j
                    y = i
                }
            }
        }

        fun leftRight(d: Int) {
            var ex = x
            var movable = false
            while (map[y][ex + d] != '#') {
                ex += d
                if (map[y][ex] == '.') {
                    movable = true
                    break
                }
            }
            while (true) {
                if (x == ex) break
                if (map[y][ex] == '.') {
                    map[y][ex] = map[y][ex - d]
                    map[y][ex - d] = '.'
                }
                ex -= d
            }
            if (movable) {
                x += d
            }
        }

        fun topBottom(psx: Int, pex: Int, cy: Int, d: Int): Boolean {
            var sx = Int.MAX_VALUE
            var ex = Int.MIN_VALUE
            for (cx in psx..pex) {
                if (map[cy - d][cx] == '.') continue
                when (map[cy][cx]) {
                    '#' -> return false // can't move
                    '[' -> {
                        sx = min(sx, cx)
                        ex = max(ex, cx + 1)
                    }

                    ']' -> {
                        sx = min(sx, cx - 1)
                        ex = max(ex, cx)
                    }
                }
            }
            if (sx <= ex) {
                if (!topBottom(sx, ex, cy + d, d)) return false
            }
            for (cx in psx..pex) {
                if (map[cy][cx] == '.' && map[cy - d][cx] != '#') {
                    map[cy][cx] = map[cy - d][cx]
                    map[cy - d][cx] = '.'
                }
            }
            if (psx == pex && map[cy][psx] == '@') y = cy
            return true
        }

        for (op in ops) {
            when (op) {
                '^' -> topBottom(x, x, y - 1, -1)
                'v' -> topBottom(x, x, y + 1, 1)
                '<' -> leftRight(-1)
                '>' -> leftRight(1)
            }
            // "======".println()
            // op.println()
            // map.joinToString("\n") { it.concatToString() }.println()
            // map[y][x].println()
        }
        var ans = 0
        for (h in 0 until H) {
            for (w in 0 until W) {
                if (map[h][w] == '[') ans += (h * 100) + w
            }
        }
        // map.joinToString("\n") { it.concatToString() }.println()
        // println(ans)
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testSmallInput = readInput("Day15_test_small")
    check(part1(testSmallInput) == 2028)
    val testLargeInput = readInput("Day15_test_large")
    check(part1(testLargeInput) == 10092)
    check(part2(testLargeInput) == 9021)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
