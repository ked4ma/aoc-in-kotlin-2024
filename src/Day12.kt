fun main() {
    val dirs = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0,
    )

    fun regionMap(input: List<String>): Pair<Array<IntArray>, Int> {
        val H = input.size
        val W = input[0].length
        val map = Array(H) { IntArray(W) { -1 } }
        var idx = 0
        run {
            for (h in 0 until H) {
                for (w in 0 until W) {
                    if (map[h][w] >= 0) continue
                    val queue = ArrayDeque<Pair<Int, Int>>()
                    queue.add(h to w)
                    map[h][w] = idx
                    while (queue.isNotEmpty()) {
                        val (ch, cw) = queue.removeFirst()
                        for ((dh, dw) in dirs) {
                            val nh = ch + dh
                            val nw = cw + dw
                            if (nh !in 0 until H || nw !in 0 until W || input[nh][nw] != input[ch][cw] || map[nh][nw] >= 0) continue
                            queue.add(nh to nw)
                            map[nh][nw] = idx
                        }
                    }
                    idx++
                }
            }
        }
        return map to idx
    }

    fun part1(input: List<String>): Int {
        val (map, idx) = regionMap(input)
        val H = map.size
        val W = map[0].size

        val plants = IntArray(idx)
        val fences = IntArray(idx)
        for (h in 0 until H) {
            for (w in 0 until W) {
                val p = map[h][w]
                plants[p]++
                for ((dh, dw) in dirs) {
                    if (h + dh !in 0 until H || w + dw !in 0 until W || map[h][w] != map[h + dh][w + dw]) {
                        fences[p]++
                    }
                }
            }
        }
        var ans = 0
        for (i in 0 until idx) {
            ans += plants[i] * fences[i]
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val (map, idx) = regionMap(input)
        val H = map.size
        val W = map[0].size

        val plants = IntArray(idx)
        val sides = IntArray(idx)

        for (h in 0 until H) {
            for (w in 0 until W) {
                plants[map[h][w]]++
                // search following types ("?" means any value is ok)
                // □□□    ?■□
                // □■■ or ■■□
                // □■?    □□□
                // top-left
                val cnt = intArrayOf(0, 0)
                if (h > 0 && w > 0 && map[h - 1][w - 1] == map[h][w]) cnt[1]++
                if (h > 0 && map[h - 1][w] == map[h][w]) cnt[0]++
                if (w > 0 && map[h][w - 1] == map[h][w]) cnt[0]++
                if (cnt[0] == 0 || (cnt[0] == 2 && cnt[1] == 0)) sides[map[h][w]]++
                // top-right
                cnt.fill(0)
                if (h > 0 && w < W - 1 && map[h - 1][w + 1] == map[h][w]) cnt[1]++
                if (h > 0 && map[h - 1][w] == map[h][w]) cnt[0]++
                if (w < W - 1 && map[h][w + 1] == map[h][w]) cnt[0]++
                if (cnt[0] == 0 || (cnt[0] == 2 && cnt[1] == 0)) sides[map[h][w]]++
                // bottom-left
                cnt.fill(0)
                if (h < H - 1 && w > 0 && map[h + 1][w - 1] == map[h][w]) cnt[1]++
                if (h < H - 1 && map[h + 1][w] == map[h][w]) cnt[0]++
                if (w > 0 && map[h][w - 1] == map[h][w]) cnt[0]++
                if (cnt[0] == 0 || (cnt[0] == 2 && cnt[1] == 0)) sides[map[h][w]]++
                // bottom-right
                cnt.fill(0)
                if (h < H - 1 && w < W - 1 && map[h + 1][w + 1] == map[h][w]) cnt[1]++
                if (h < H - 1 && map[h + 1][w] == map[h][w]) cnt[0]++
                if (w < W - 1 && map[h][w + 1] == map[h][w]) cnt[0]++
                if (cnt[0] == 0 || (cnt[0] == 2 && cnt[1] == 0)) sides[map[h][w]]++
            }
        }

        var ans = 0
        for (i in 0 until idx) {
            ans += plants[i] * sides[i]
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
