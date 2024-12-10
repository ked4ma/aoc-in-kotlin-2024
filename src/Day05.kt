fun main() {
    fun part1(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotBlank() }
        val updates = input.slice(rules.size + 1 until input.size)

        val ruleMap = mutableMapOf<Int, MutableSet<Int>>()
        for (rule in rules) {
            val (a, b) = rule.split("|").map { it.toInt() }
            ruleMap.putIfAbsent(a, mutableSetOf())
            ruleMap.getValue(a).add(b)
        }

        var ans = 0
        for (update in updates) {
            val list = update.split(",").map { it.toInt() }.toMutableList()
            var ok = true
            for (i in list.lastIndex downTo 1) {
                if (!ok) break
                val rule = ruleMap.getOrDefault(list[i], emptySet())
                for (j in 0 until i) {
                    if (list[j] in rule) {
                        ok = false
                        break
                    }
                }
            }
            if (ok) {
                ans += list[list.size / 2]
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotBlank() }
        val updates = input.slice(rules.size + 1 until input.size)

        val ruleMap = mutableMapOf<Int, MutableSet<Int>>()
        for (rule in rules) {
            val (a, b) = rule.split("|").map { it.toInt() }
            ruleMap.putIfAbsent(a, mutableSetOf())
            ruleMap.getValue(a).add(b)
        }

        fun order(list: List<Int>): List<Int> {
            if (list.size <= 1) return list
            val left = mutableListOf<Int>()
            val right = mutableListOf<Int>()
            val rule = ruleMap.getOrDefault(list.last(), emptySet())
            for (j in 0 until list.size - 1) {
                if (list[j] in rule) {
                    right.add(list[j])
                } else {
                    left.add(list[j])
                }
            }
            return order(left) + list.last() + order(right)
        }

        var ans = 0
        for (update in updates) {
            val list = update.split(",").map { it.toInt() }.toMutableList()
            var ok = true
            for (i in list.lastIndex downTo 1) {
                if (!ok) break
                val rule = ruleMap.getOrDefault(list[i], emptySet())
                for (j in 0 until i) {
                    if (list[j] in rule) {
                        ok = false
                        break
                    }
                }
            }
            if (ok) continue
            ans += order(list)[list.size / 2]
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
