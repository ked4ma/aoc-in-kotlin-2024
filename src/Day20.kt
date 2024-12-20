fun main() {
    fun parseStartAndEnd(input: List<String>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var sh = 0
        var sw = 0
        var eh = 0
        var ew = 0
        for (h in range(input.size)) {
            for (w in range(input[0].length)) {
                when (input[h][w]) {
                    'S' -> {
                        sh = h
                        sw = w
                    }

                    'E' -> {
                        eh = h
                        ew = w
                    }
                }
            }
        }
        return (sh to sw) to (eh to ew)
    }

    val dirs = listOf(
        1 to 0,
        -1 to 0,
        0 to 1,
        0 to -1,
    )

    fun costFrom(input: List<String>, from: Pair<Int, Int>): Array<IntArray> {
        val H = input.size
        val W = input[0].length
        val res = Array(H) { IntArray(W) { Int.MAX_VALUE } }
        res[from.first][from.second] = 0
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(from)
        while (queue.isNotEmpty()) {
            val (h, w) = queue.removeFirst()
            if (input[h][w] == '#') continue
            for ((dh, dw) in dirs) {
                val nh = h + dh
                val nw = w + dw
                if (nh !in range(H) || nw !in range(W) || input[nh][nw] == '#' || res[nh][nw] < Int.MAX_VALUE) continue
                res[nh][nw] = res[h][w] + 1
                queue.add(nh to nw)
            }
        }
        return res
    }

    fun part1(input: List<String>, threshold: Int = 100): Int {
        val H = input.size
        val W = input[0].length
        val (s, e) = parseStartAndEnd(input)
        val forward = costFrom(input, s)
        val backward = costFrom(input, e)
        val baseCost = forward[e.first][e.second]
        val dirs2 = listOf(
            2 to 0,
            -2 to 0,
            0 to 2,
            0 to -2,
            1 to 1,
            1 to -1,
            -1 to 1,
            -1 to -1,
        )
        var ans = 0
        for (h in range(H)) {
            for (w in range(W)) {
                if (input[h][w] == '#') continue
                for ((dh, dw) in dirs2) {
                    val nh = h + dh
                    val nw = w + dw
                    if (nh !in range(H) || nw !in range(W) || input[nh][nw] == '#') continue
                    if (baseCost - (forward[h][w] + backward[nh][nw] + 2) >= threshold) ans++
                }
            }
        }

        return ans
    }

    fun part2(input: List<String>, threshold: Int = 100): Int {
        val H = input.size
        val W = input[0].length
        val (s, e) = parseStartAndEnd(input)
        val forward = costFrom(input, s)
        val backward = costFrom(input, e)
        val baseCost = forward[e.first][e.second]
        var ans = 0
        for (i in 2..20) {
            val dirs2 = buildSet {
                for (j in 0..i) {
                    val k = i - j
                    add(j to k)
                    add(j to -k)
                    add(-j to k)
                    add(-j to -k)
                }
            }
            for (h in range(H)) {
                for (w in range(W)) {
                    if (input[h][w] == '#') continue
                    for ((dh, dw) in dirs2) {
                        val nh = h + dh
                        val nw = w + dw
                        if (nh !in range(H) || nw !in range(W) || input[nh][nw] == '#') continue
                        if (baseCost - (forward[h][w] + backward[nh][nw] + i) >= threshold) ans++
                    }
                }
            }
        }

        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day20_test")
    check(part1(testInput, 2) == 44)
    check(part1(testInput, 4) == 30)
    check(part2(testInput, 50) == 285)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
