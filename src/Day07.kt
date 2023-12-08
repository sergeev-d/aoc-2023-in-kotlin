private const val CURRENT_DAY = "Day07"
fun main() {
    fun <T, U> pairComparator(
        firstComparator: Comparator<T>,
        secondComparator: Comparator<U>
    ): Comparator<Pair<T, U>> =
        compareBy(firstComparator) { p: Pair<T, U> -> p.first }
            .thenBy(secondComparator) { p: Pair<T, U> -> p.second }

    fun Char.toRank(witJoker: Boolean): Int {
        return when (this) {
            in '1'..'9' -> this.digitToInt()
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> if (witJoker) 1 else 11
            'T' -> 10
            else -> 0
        }
    }

    fun process(input: List<String>, withJoker: Boolean): Int {
        return input.map { it.split("\\s+".toRegex()) }
            .associate { (k, v) -> k to v.toInt() }
            .mapKeys { (k, _) ->
                val cards = k.groupingBy { it }.eachCount().toMutableMap()

                if (withJoker) {
                    val jokers = cards.remove('J') ?: 0
                    if (cards.isNotEmpty()){
                        val (maxK, maxV) = cards.maxBy { it.value }
                        cards[maxK] = maxV + jokers
                    } else {
                        cards['J'] = jokers
                    }
                }

                cards.values.sumOf { (2 shl it) - 1 } to k
            }
            .toSortedMap(pairComparator(naturalOrder()) { o1, o2 ->
                if (o1 != o2) {
                    o1.toCharArray().indices.map { i ->
                        o1[i].toRank(withJoker) compareTo o2[i].toRank(withJoker)
                    }.first { it != 0 }
                } else {
                    0
                }
            })
            .values.mapIndexed { i, v -> (i + 1) * v }
            .sum()
    }

    fun part1(input: List<String>): Int {
        return process(input, false)
    }


    fun part2(input: List<String>): Int {
        return process(input, true)
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}
