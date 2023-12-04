private const val CURRENT_DAY = "Day02"

val rgb = mapOf(
    'r' to Color.Red().name,
    'g' to Color.Green().name,
    'b' to Color.Blue().name
)

sealed class Color(open val name: String, open var cubeSize: Int) {
    data class Red(override val name: String = "red", override var cubeSize: Int = 0) : Color(name, cubeSize)

    data class Blue(override val name: String = "blue", override var cubeSize: Int = 0) : Color(name, cubeSize)

    data class Green(override val name: String = "green", override var cubeSize: Int = 0) : Color(name, cubeSize)

    fun nameLength(): Int {
        return name.length
    }
}

private fun initCubeColors(redSize: Int = 0, blueSize: Int = 0, greenSize: Int = 0): HashMap<String, Color> {
    val red = Color.Red(cubeSize = redSize)
    val blue = Color.Blue(cubeSize = blueSize)
    val green = Color.Green(cubeSize = greenSize)

    return hashMapOf(
        Pair<String, Color>(red.name, red),
        Pair<String, Color>(blue.name, blue),
        Pair<String, Color>(green.name, green)
    )
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.mapIndexed { i, s ->
            p21(s, initCubeColors(12, 14, 13)) { cubeSize, digit ->
                if (cubeSize >= digit) i + 1 else 0
            }
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { s -> p22(s, initCubeColors()) }
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}


private fun p21(s: String, cubes: HashMap<String, Color>, action: (cubeSize: Int, digit: Int) -> Int): Int {
    var index = 0
    var d = ""
    var result = -1

    val str = s.substring(s.indexOf(':') + 1, s.length)

    while (index < str.length) {
        var j = 1

        when (val char = str[index]) {
            in '0'..'9' -> {
                d += str[index]
            }

            'r', 'g', 'b' -> {
                val color = cubes[rgb[char]]!!
                j = color.nameLength() - 1
                result = action(color.cubeSize, d.toInt())
            }

            ',', ';' -> {
                d = ""
            }
        }

        if (result == 0) break
        index += j
    }

    return result
}

private fun p22(s: String, cubes: HashMap<String, Color>): Int {
    var index = 0
    var digits = ""

    val str = s.substring(s.indexOf(':') + 1, s.length)

    while (index < str.length) {
        var j = 1

        when (val char = str[index]) {
            in '0'..'9' -> {
                digits += str[index]
            }

            'r', 'g', 'b' -> {
                val color = cubes[rgb[char]]!!
                j = color.nameLength() - 1
                color.cubeSize = maxOf(color.cubeSize, digits.toInt())
                cubes[rgb[char]!!] = color
            }

            ',', ';' -> {
                digits = ""
            }
        }

        index += j
    }

    return cubes.values.map { it.cubeSize }.reduce { acc, v -> acc * v }
}

