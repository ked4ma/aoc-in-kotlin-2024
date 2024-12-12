fun main() {
    fun part1(input: List<String>): Int {
        val H = input.size
        val W = input[0].length
        val heads = buildList {
            for (h in 0 until H) {
                for (w in 0 until W) {
                    if (input[h][w] == '0') {
                        add(h to w)
                    }
                }
            }
        }
        var ans = 0
        val dirs = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
        )
        for ((th, tw) in heads) {
            val vis = Array(H) { BooleanArray(W) }
            val queue = ArrayDeque<Pair<Int, Int>>()
            queue.add(th to tw)
            vis[th][tw] = true
            while (queue.isNotEmpty()) {
                val (h, w) = queue.removeFirst()
                for ((dh, dw) in dirs) {
                    val nh = h + dh
                    val nw = w + dw
                    if (nh !in 0 until H || nw !in 0 until W || vis[nh][nw] || input[h][w] + 1 != input[nh][nw]) continue
                    queue.add(nh to nw)
                    vis[nh][nw] = true
                    if (input[nh][nw] == '9') ans++
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val H = input.size
        val W = input[0].length
        val heads = buildList {
            for (h in 0 until H) {
                for (w in 0 until W) {
                    if (input[h][w] == '0') {
                        add(h to w)
                    }
                }
            }
        }
        var ans = 0
        val dirs = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
        )
        for ((th, tw) in heads) {
            val cnt = Array(H) { IntArray(W) }
            val queue = ArrayDeque<Pair<Int, Int>>()
            queue.add(th to tw)
            cnt[th][tw] = 1
            repeat(9) { i ->
                val next = mutableSetOf<Pair<Int, Int>>()
                repeat(queue.size) {
                    val (h, w) = queue.removeFirst()
                    for ((dh, dw) in dirs) {
                        val nh = h + dh
                        val nw = w + dw
                        if (nh !in 0 until H || nw !in 0 until W || input[h][w] + 1 != input[nh][nw]) continue
                        next.add(nh to nw)
                        cnt[nh][nw] += cnt[h][w]
                    }
                }
                queue.addAll(next)
            }
            for (h in 0 until H) {
                for (w in 0 until W) {
                    if (input[h][w] == '9') ans += cnt[h][w]
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
