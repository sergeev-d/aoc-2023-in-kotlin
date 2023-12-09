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

    fun part1(input: List<String>): Int {
        return input.sumOf { s ->
            findNextValue(s.split("\\s".toRegex()).map { it.toInt() }, isLast = true)
        }
    }


    fun part2(input: List<String>): Int {
        return input.sumOf { s ->
            findNextValue(s.split("\\s".toRegex()).map { it.toInt() }, isLast = false)
        }
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}

private fun process(): Int {
    return 1
}
