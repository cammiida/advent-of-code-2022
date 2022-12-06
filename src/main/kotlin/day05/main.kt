package day05

import java.io.File

data class RearrangementStep(
    val numberOfItems: Int,
    val fromCol: Int,
    val toCol: Int
)

fun parseRearrangementSteps(steps: List<String>): List<RearrangementStep> {
    return steps.map { step ->
        val stepElements = step.split(" ")
        RearrangementStep(
            numberOfItems = stepElements[1].toInt(),
            fromCol = stepElements[3].toInt() - 1,
            toCol = stepElements[5].toInt() - 1
        )
    }
}

fun createStacks(input: List<String>, stackIndexes: String): List<List<Char>> {
    val numberOfStacks = stackIndexes.trim().last().toString().toInt()
    val stacks: List<MutableList<Char>> = List(numberOfStacks) {
        mutableListOf()
    }
    input.forEach {
        it.forEachIndexed { index, c ->
            if (c.isUpperCase()) {
                val stackIndex = stackIndexes[index].toString().toInt() - 1
                stacks[stackIndex].add(c)
            }
        }
    }
    return stacks.map { stack -> stack.reversed() }
}

fun executeSteps9000(stacks: List<List<Char>>, steps: List<RearrangementStep>): List<List<Char>> {
    val stackCopy: List<MutableList<Char>> = stacks.map { it.toMutableList() }
    steps.forEach { step ->
        repeat(step.numberOfItems) {
            stackCopy[step.toCol].add(stackCopy[step.fromCol].removeLast())
        }
    }
    return stackCopy
}

fun executeSteps9001(stacks: List<List<Char>>, steps: List<RearrangementStep>): List<List<Char>> {
    val stackCopy: List<MutableList<Char>> = stacks.map { it.toMutableList() }
    steps.forEach { step ->
        val itemsToMove = mutableListOf<Char>()
        repeat(step.numberOfItems) {
            itemsToMove.add(stackCopy[step.fromCol].removeLast())
        }
        stackCopy[step.toCol].addAll(itemsToMove.reversed())
    }
    return stackCopy
}

fun part1(stacks: List<List<Char>>, rearrangementSteps: List<RearrangementStep>) {
    val resultStacks = executeSteps9000(stacks, rearrangementSteps)
    val result = resultStacks.map { it.last() }.joinToString("")
    println(result)
}

fun part2(stacks: List<List<Char>>, rearrangementSteps: List<RearrangementStep>) {
    val resultStacks = executeSteps9001(stacks, rearrangementSteps)
    val result = resultStacks.map { it.last() }.joinToString("")
    println(result)
}

fun main() {
    val input = File("input.txt").readLines()
    val separatorIndex = input.indexOfFirst { it.isEmpty() }
    val unprocessedStacks = input.subList(0, separatorIndex - 1)
    val stackIndexes = input[separatorIndex - 1]
    val unparsedRearrangementSteps = input.subList(separatorIndex + 1, input.size)

    val startingStacks = createStacks(unprocessedStacks, stackIndexes)
    val rearrangementSteps = parseRearrangementSteps(unparsedRearrangementSteps)

    part1(startingStacks, rearrangementSteps)
    part2(startingStacks, rearrangementSteps)
}