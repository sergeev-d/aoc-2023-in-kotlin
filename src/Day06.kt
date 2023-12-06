private const val CURRENT_DAY = "Day06"
fun main() {
    fun findWinAttempts(time: Long, distance: Long) = (1..time).count { v ->
        val currentDistance = v * (time - v)
        currentDistance > distance
    }

    fun part1(input: List<String>): Int {
        val (times, distances) = input.map { s ->
            s.substringAfter(":").trim().split("\\s+".toRegex()).map { it.toLong() }
        }

        return times.mapIndexed { i, time ->
            findWinAttempts(time, distances[i])
        }.reduce { acc, i -> acc * i }

    }

    fun part2(input: List<String>): Int {
        val (time, distance) = input.map { s ->
            s.substringAfter(":").replace("\\s+".toRegex(), "").toLong()
        }

        return findWinAttempts(time, distance)
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}


