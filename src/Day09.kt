private const val CURRENT_DAY = "Day09"
fun main() {
    fun findNextValue(values: List<Int>, isLast: Boolean): Int {
        val value = if (isLast) values.last() else values.first()
        val mappedValues = mutableListOf<Int>()

        for (i in 0 until values.size - 1) {
            mappedValues.add(values[i + 1] - values[i])
        }

        return if (mappedValues.all { it == 0 }) {
            value
        } else if (isLast) {
            findNextValue(mappedValues, true) + value
        } else {
            value - findNextValue(mappedValues, false)
        }
    }

    fun findNextValue2(values: List<Int>): Sequence<List<Int>> {
        return generateSequence(values) { currentValues ->
            if (currentValues.any { it != 0 }) {
                currentValues.windowed(2) { (v1, v2) -> v2 - v1 }
            } else {
                null
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { s ->
            findNextValue2(s.split("\\s".toRegex()).map { it.toInt() }).sumOf { it.last() }
        }
    }


    fun part2(input: List<String>): Int {
        return input.sumOf { s ->
            findNextValue2(s.split("\\s".toRegex()).map { it.toInt() }).map { v -> v.first() }.toList()
                .reduceRight(Int::minus)
        }
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}
