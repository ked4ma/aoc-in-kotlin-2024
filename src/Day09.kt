fun main() {
    fun decompress(input:List<String>): IntArray {
        val len = input[0].map { it - '0' }.sum()
        val arr = IntArray(len) { -1 }
        run {
            var id = 0
            var i = 0
            for (j in 0 until input[0].length) {
                if (j % 2 == 0) {
                    repeat(input[0][j] - '0') {
                        arr[i] = id
                        i++
                    }
                    id++
                } else {
                    i += input[0][j] - '0'
                }
            }
        }
        return arr
    }

    fun part1(input: List<String>): Long {
        val arr = decompress(input)

        var i = 0
        var j = arr.lastIndex
        while (i < j) {
            if (arr[i] >= 0) {
                i++
                continue
            }
            if (arr[j] < 0) {
                j--
                continue
            }
            val tmp = arr[i]
            arr[i] = arr[j]
            arr[j] = tmp
        }
        var ans = 0L
        for (k in arr.indices) {
            if (arr[k] < 0) break
            ans += k * arr[k]
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val arr = decompress(input)

        var j = arr.lastIndex
        while (j >= 0) {
            if (arr[j] < 0) {
                j--
                continue
            }
            val id = arr[j]
            var l = 1
            while (j - 1 >= 0 && arr[j - 1] == id) {
                j--
                l++
            }
            var i = 0
            while (i < j) {
                if (arr[i] >= 0) {
                    i++
                    continue
                }
                var s = 1
                while (i + 1 < j && arr[i + 1] < 0) {
                    i++
                    s++
                }
                if (l <= s) {
                    i -= s - 1
                    for (k in 0 until l) {
                        val tmp = arr[i + k]
                        arr[i + k] = arr[j + k]
                        arr[j + k] = tmp
                    }
                    break
                }
                i++
            }
            j--
        }

        var ans = 0L
        for (k in arr.indices) {
            if (arr[k] < 0) continue
            ans += k * arr[k]
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
