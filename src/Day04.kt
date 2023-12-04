private const val CURRENT_DAY = "Day04"
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { s -> 1 shl (findMatched(s) - 1) } // or power of 2
    }

    fun part2(input: List<String>): Int {
        val cards = MutableList(input.size) { 1 } // each card will be processed 1 time

        for (i in input.indices) {
            for (j in 1..findMatched(input[i])) {
                cards[i + j] += cards[i]
            }
        }

        return cards.sum()
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}

private fun findMatched(s: String): Int {
    val space = "\\s+".toRegex()
    // left and right parts have unique numbers

    return s.substringAfter(":")
        .replace("|", "")
        .trim()
        .split(space)
        .groupingBy { v -> v.toInt() }
        .eachCount()
        .filter { (_, v) -> v > 1 }
        .count()
}
