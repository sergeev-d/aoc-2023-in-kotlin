import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

private const val CURRENT_DAY = "Day05"
fun main() {
    fun part1(input: List<String>): Int {
        val inputAsChars = input.flatMap { it.toCharArray().plus(' ').asList() }
        return findMinOfLocation(inputAsChars, ::transformSeedToTempRanges).toInt()
    }

    fun part2(input: List<String>): Int {
        val inputAsChars = input.flatMap { it.toCharArray().plus(' ').asList() }
        return findMinOfLocation(inputAsChars, ::transformSeedToRanges).toInt()
    }

    val input = readInput(CURRENT_DAY)
    val t1 = measureTime { part1(input).println() }
    val t2 = measureTime { part2(input).println() }
    t1.println()
    t2.println()
}

private fun findMinOfLocation(input: List<Char>, transform: (list: List<Long>) -> List<LongRange>): Long {
    var i = 0

    val seeds = mutableListOf<LongRange>()
    val levels = mutableListOf<List<RangeValue>>()

    var isSeedRow = true
    var currentDigit = ""
    var tempList = mutableListOf<Long>()

    while (i < input.size) {
        when (val c = input[i]) {
            ':' -> {
                if (isSeedRow && tempList.isNotEmpty()) {
                    isSeedRow = false
                    seeds.addAll(transform(tempList))
                    tempList = mutableListOf()
                    continue
                }

                if (!isSeedRow && tempList.isNotEmpty()) {
                    levels.add(transformToRanges(tempList))
                    tempList = mutableListOf()
                }
            }

            in '0'..'9' -> {
                currentDigit += c
            }

            else -> {
                if (currentDigit.isNotEmpty()) {
                    tempList.add(currentDigit.toLong())
                }

                currentDigit = ""
            }
        }

        i += 1
    }

    // add last level
    levels.add(transformToRanges(tempList))

    return seeds.minOf { seedRange ->
        return@minOf runBlocking(Dispatchers.Default) {
            (seedRange.first..seedRange.last step 64).map { seed ->
                async {
                    var mapping = seed
                    for (idx in levels.indices) {
                        mapping = levels[idx].find { mapping in it.range }?.let { mapping + it.offset } ?: mapping
                    }

                    mapping
                }
            }.awaitAll().minOf { it }
        }
    }
}

private fun transformToRanges(list: MutableList<Long>): List<RangeValue> = buildList {
    for (i in 0 until list.size step 3) {
        val dest = list[i]
        val src = list[i + 1]
        val range = list[i + 2]

        add(RangeValue(range = src until (src + range), offset = dest - src))
    }
}

private fun transformSeedToRanges(seeds: List<Long>): List<LongRange> = buildList {
    seeds.chunked(2) { (start, size) ->
        (start until start + size).also {
            add(it)
        }
    }
}

private fun transformSeedToTempRanges(seeds: List<Long>): List<LongRange> = buildList {
    seeds.forEach { seed ->
        add(LongRange(seed, seed))
    }
}

internal data class RangeValue(val range: LongRange, val offset: Long = 0)
