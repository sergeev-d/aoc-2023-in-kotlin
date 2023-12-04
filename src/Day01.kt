private const val CURRENT_DAY = "Day01"
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { s -> p11(s) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { s -> p12(s) }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}

private fun p11(s: String): Int {
    var first = false
    var second = false
    val l = s.length
    var result = 0

    for (i in 0 until l) {
        if (first && second) {
            break
        }

        if (!first && s[i].isDigit()) {
            first = true
            result += Character.getNumericValue(s[i]) * 10
        }

        if (!second && s[l - i - 1].isDigit()) {
            second = true
            result += Character.getNumericValue(s[l - i - 1])
        }

    }

    return result
}

private fun p12(s: String): Int {
    val words = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    var result = ""
    var i = 0

    while (i < s.length) {
        if (s[i].isDigit()) {
            result += s[i]
            i += 1
            continue
        }

        for (j in words.indices) {
            if (s.length >= words[j].length + i && s.substring(i, i + (words[j].length)) == words[j]) {
                result += ('1' + j).toString()
                i += (words[j].length - 1) - 1
                break
            }
        }

        i += 1
    }

    return 10 * (result[0] - '0') + (result[result.length - 1] - '0')
}
