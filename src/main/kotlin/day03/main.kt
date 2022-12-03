package day03

import java.io.File

fun findCommonItem(vararg compartments: String): Char {
    return compartments.fold(compartments.first().toSet()) { acc, s ->
        acc.intersect(s.toSet())
    }.first()
}

fun getItemValue(char: Char): Int {
    if (char.isLowerCase()) {
        return char.code - 96
    } else if (char.isUpperCase()) {
        return char.code - 38
    }
    throw Error("$char is not a valid character")
}

fun part1(): Int {
    val input: List<Pair<String, String>> = File("input.txt")
        .readLines()
        .map {
            Pair(
                it.substring(0, (it.length / 2)),
                it.substring((it.length / 2))
            )
        }

    var totalSum = 0
    input.forEach { rucksack ->
        val commonItem = findCommonItem(rucksack.first, rucksack.second)
        totalSum += getItemValue(commonItem)
    }
    return totalSum
}

fun part2(): Int {
    val triplets = File("input.txt").readLines().chunked(3)

    var totalSum = 0
    triplets.forEach { triplet ->
        val commonItem = findCommonItem(*triplet.toList().toTypedArray())
        totalSum += getItemValue(commonItem)
    }

    return totalSum
}

fun main() {
    println(part1())
    println(part2())
}