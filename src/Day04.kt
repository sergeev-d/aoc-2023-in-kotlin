private const val CURRENT_DAY = "Day04"
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { s -> p41(s) }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}

private fun p41(s: String): Int {
    val space = "\\s+".toRegex()

    // winners cards 2^N where N = count of matched numbers in a card
    // left and right parts have unique numbers

    val matched = s.substring(s.indexOf(":") + 1, s.length)
        .replace("|", "")
        .trim()
        .split(space)
        .groupingBy { v -> v.toInt() }
        .eachCount()
        .filter { (_, v) -> v > 1 }
        .count()

    return 1 shl (matched - 1) // can be changed to 2 pow
}

private fun p42(s: String): Int {
    return 0
}
