fun main() {
    data class Robot(val x: Int, val y: Int, val vx: Int, val vy: Int)

    val reg = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)".toRegex()

    fun part1(input: List<String>, W: Int = 101, H: Int = 103): Long {
        val robots = input.map {
            reg.find(it)!!.groupValues
                .let { (_, x, y, vx, vy) ->
                    Robot(x.toInt(), y.toInt(), vx.toInt(), vy.toInt())
                }
        }

        val cnt = IntArray(4)
        for ((x, y, vx, vy) in robots) {
            val x2 = (x + vx * 100).mod(W)
            val y2 = (y + vy * 100).mod(H)
            when {
                x2 < W / 2 && y2 < H / 2 -> cnt[0]++
                x2 < W / 2 && y2 > H / 2 -> cnt[1]++
                x2 > W / 2 && y2 < H / 2 -> cnt[2]++
                x2 > W / 2 && y2 > H / 2 -> cnt[3]++
            }
        }
        return cnt.fold(1L) { acc, c -> acc * c }
    }

    fun part2(input: List<String>, W: Int = 101, H: Int = 103): Long {
        val robots = input.map {
            reg.find(it)!!.groupValues
                .let { (_, x, y, vx, vy) ->
                    Robot(x.toInt(), y.toInt(), vx.toInt(), vy.toInt())
                }
        }
        var i = 0L
        while (true) {
            val arr = Array(H) { CharArray(W) { '.' } }
            val cntH = IntArray(H)
            val cntW = IntArray(W)
            for ((x, y, vx, vy) in robots) {
                val x2 = (x + vx * i).mod(W)
                val y2 = (y + vy * i).mod(H)
                arr[y2][x2] = '#'
                cntH[y2]++
                cntW[x2]++
            }
            if (cntH.max() >= 30 && cntW.max() >= 30) {
                println("-----")
                println(arr.joinToString("\n") { it.concatToString() })
                break
            }
            i++
        }
        return i
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day14_test")
    check(part1(testInput, 11, 7) == 12L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
