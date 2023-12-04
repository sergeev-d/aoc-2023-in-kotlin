fun main() {
    fun part1(input: List<String>): Int {
        return input.mapIndexed { i, s -> if (p21(s)) i + 1 else 0 }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { s -> p22(s) }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun p21(s: String): Boolean {
    val r = "red"
    val b = "green"
    val g = "blue"

    val cubes = hashMapOf(Pair(r, 12), Pair(g, 13), Pair(b, 14))

    var i = 0
    var d = ""
    var result = true

    val ss = s.substring(s.indexOf(':') + 1, s.length)

    while (i < ss.length) {
        var j = 1

        when (ss[i]) {
            in '0'..'9' -> {
                d += ss[i]
            }

            'r' -> {
                j = r.length - 1
                result = cubes[r]!! >= d.toInt()
            }

            'g' -> {
                j = g.length - 1
                result = cubes[g]!! >= d.toInt()
            }

            'b' -> {
                j = b.length - 1
                result = cubes[b]!! >= d.toInt()
            }

            ',', ';' -> {
                d = ""
            }
        }

        if (!result) break

        i += j
    }

    return result
}

private fun p22(s: String): Int {
    val r = "red"
    val g = "green"
    val b = "blue"

    val cubes = mutableMapOf<String, Int>()

    var i = 0
    var d = ""

    val ss = s.substring(s.indexOf(':') + 1, s.length)

    while (i < ss.length) {
        var j = 1

        when (ss[i]) {
            in '0'..'9' -> {
                d += ss[i]
            }

            'r' -> {
                j = r.length - 1
                cubes[r] = maxOf(cubes[r] ?: 0, d.toInt())
            }

            'g' -> {
                j = g.length - 1
                cubes[g] = maxOf(cubes[g] ?: 0, d.toInt())
            }

            'b' -> {
                j = b.length - 1
                cubes[b] = maxOf(cubes[b] ?: 0, d.toInt())
            }

            ',', ';' -> {
                d = ""
            }
        }

        i += j
    }

    return cubes.values.reduce { acc, v -> acc * v }
}