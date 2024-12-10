fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        val dirs = listOf(
            -1 to 0,
            0 to 1,
            1 to 0,
            0 to -1,
        )
        val H = input.size
        val W = input[0].length
        var gH = 0
        var gW = 0
        var dirIndex = 0
        for (h in 0 until H) {
            for (w in 0 until W) {
                if (map[h][w] == '^') {
                    gH = h
                    gW = w
                }
            }
        }
        map[gH][gW] = 'X'
        var ans = 1
        while (gH + dirs[dirIndex].first in 0 until H && gW + dirs[dirIndex].second in 0 until W) {
            val nextH = gH + dirs[dirIndex].first
            val nextW = gW + dirs[dirIndex].second
            if (map[nextH][nextW] == '#') {
                dirIndex = (dirIndex + 1) % dirs.size
            } else {
                gH = nextH
                gW = nextW
                if (map[gH][gW] != 'X') ans++
                map[gH][gW] = 'X'
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val dirs = listOf(
            -1 to 0,
            0 to 1,
            1 to 0,
            0 to -1,
        )
        val H = input.size
        val W = input[0].length
        var gH = 0
        var gW = 0
        for (h in 0 until H) {
            for (w in 0 until W) {
                if (input[h][w] == '^') {
                    gH = h
                    gW = w
                }
            }
        }
        var ans = 0
        for (oh in 0 until H) {
            for (ow in 0 until W) {
                if (input[oh][ow] == '#') continue
                val map = input.map { it.toCharArray() }
                map[oh][ow] = '#'
                var h = gH
                var w = gW
                var dirIndex = 0
                map[h][w] = dirIndex.digitToChar()
                while (h + dirs[dirIndex].first in 0 until H && w + dirs[dirIndex].second in 0 until W) {
                    val nextH = h + dirs[dirIndex].first
                    val nextW = w + dirs[dirIndex].second
                    if (map[nextH][nextW] == '#') {
                        dirIndex = (dirIndex + 1) % dirs.size
                    } else if (map[nextH][nextW] == dirIndex.digitToChar()) {
                        ans++
                        break
                    } else {
                        h = nextH
                        w = nextW
                        map[h][w] = dirIndex.digitToChar()
                    }
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 41)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
