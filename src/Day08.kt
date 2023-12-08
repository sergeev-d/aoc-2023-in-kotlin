private const val CURRENT_DAY = "Day08"
fun main() {
    fun part1(input: List<String>): Long {
        return process(input, endWith = "ZZZ") { s -> s == "AAA" }
    }


    fun part2(input: List<String>): Long {
        return process(input, endWith = "Z") { s -> s.last() == 'A' }
    }

    val input = readInput(CURRENT_DAY)
    part1(input).println()
    part2(input).println()
}

private fun process(input: List<String>, endWith: String, startWith: (s: String) -> Boolean): Long {
    val instructions = input[0]
    val dictionary = mutableMapOf<String, NodesContainer>()

    for (i in 2 until input.size) {
        val path = input[i].split("=")
        val position = path[0].trim()
        val nodes = path[1].split(",")
            .map { node -> String(node.toCharArray().filter { it.isLetterOrDigit() }.toCharArray()) }

        dictionary[position] = NodesContainer(nodes.first(), nodes.last())
    }

    val instructionCount = instructions.length

    return dictionary.filter { e -> startWith(e.key) }.keys.map { node ->
        var steps = countSteps(dictionary, node, endWith, instructions).toLong()

        if (steps % instructionCount != 0.toLong()) steps *= 2

        (steps / instructionCount)
    }.reduce(Long::times) * instructionCount
}

private fun countSteps(
    dictionary: MutableMap<String, NodesContainer>,
    node: String,
    endWith: String,
    instructions: String
): Int {
    var currentNode = node
    var steps = 0
    var found = false
    var instructionIndex = 0

    do {
        currentNode = dictionary[currentNode]?.getNode(instructions[instructionIndex]) ?: currentNode

        if (currentNode.endsWith(endWith)) found = true
        else {
            instructionIndex += 1
            instructionIndex %= instructions.length
        }

        steps += 1
    } while (!found)

    return steps
}

data class NodesContainer(val left: String, val right: String) {
    fun getNode(nodeInstruction: Char) = when (nodeInstruction) {
        'L' -> left
        'R' -> right
        else -> error("Unexpected char")
    }
}
