fun main() {
    fun part1(input: List<String>): Int {
        return p31(input.map { it.toCharArray() })
    }

    fun part2(input: List<String>): Int {
        return p32(input.map { it.toCharArray() })
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

private const val DOT = '.'
private fun p31(rows: List<CharArray>): Int {
    var sumEngine = 0

    rows.forEachIndexed { i, r ->
        var digits = ""
        var adj = false

        r.forEachIndexed { j, c ->
            if (c.isDigit()) {
                digits += c

                if (!adj) {
                    adj = if ((i == 0 && j == 0) || (i == 0 && j == r.size - 1)) {
                        rows[i + 1][j].isSpecial()
                    } else if ((i == rows.size - 1 && j == 0) || ((i == rows.size - 1 && j == r.size - 1))) {
                        rows[i + 1][j].isSpecial()
                    } else {
                        hasNeighborAsSymbol(rows, i, j, rows.size, r.size)
                    }
                }
            }

            if (digits.isNotEmpty() && (c == DOT || c.isSpecial()) || j == r.size - 1) {
                if (adj) sumEngine += digits.toInt()
                digits = ""
                adj = false
            }
        }
    }

    return sumEngine
}

private fun p32(rows: List<CharArray>): Int {
    var sumEngine = 0

    rows.forEachIndexed { i, row ->
        row.forEachIndexed { j, column ->
            if (column.isStar()) {
                val numbers = neighbors(i, j)
                    .filter { (r, c) -> r in rows.indices && c in rows.first().indices }
                    .map { (r, c) -> rows[r].findNumberAt(c) }
                    .filter { it != 0 }
                    .toList()

                if (numbers.size == 2) {
                    sumEngine += numbers.reduce { acc, i -> acc * i }
                }
            }
        }
    }

    return sumEngine
}

private fun CharArray.findNumberAt(idx: Int): Int {
    if (!this[idx].isDigit()) return 0

    var start = 0
    for (i in idx downTo 0) {
        if (this[i].isDigit()) start = i else break
    }
    var end = 0
    for (i in idx..lastIndex) {
        if (this[i].isDigit()) end = i else break
    }

    val number = String(this.sliceArray(start..end)).toInt()

    // set dots for found numbers
    for (i in start..end) this[i] = DOT

    return number
}

private fun neighbors(r: Int, c: Int): Sequence<Pair<Int, Int>> = sequenceOf(
    r - 1 to c - 1, // top left
    r - 1 to c,     // top
    r - 1 to c + 1, // top right
    r to c - 1,     // left
    r to c + 1,     // right
    r + 1 to c - 1, // bottom left
    r + 1 to c,     // bottom
    r + 1 to c + 1, // bottom right
)

private fun hasNeighborAsSymbol(m: List<CharArray>, i: Int, j: Int, r: Int, c: Int): Boolean {
    val minR = maxOf(i - 1, 0)
    val maxR = minOf(i + 1, r - 1)
    val maxC = minOf(j + 1, c - 1)
    val minC = maxOf(j - 1, 0)

    return m[maxR][minC].isSpecial() ||
            m[maxR][j].isSpecial() ||
            m[maxR][maxC].isSpecial() ||
            m[minR][minC].isSpecial() ||
            m[minR][j].isSpecial() ||
            m[minR][maxC].isSpecial() ||
            m[i][minC].isSpecial() ||
            m[i][maxC].isSpecial()
}

private fun Char.isSpecial(): Boolean {
    return this != '.' && !this.isDigit() && !this.isLetter()
}

private fun Char.isStar(): Boolean {
    return this == '*'
}
