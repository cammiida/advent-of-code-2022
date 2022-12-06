package day04

import java.io.File

fun createSectionList(first: Int, last: Int): List<Int> {
    return (first..last).fold(listOf()) { acc: List<Int>, i: Int ->
        listOf(*acc.toTypedArray(), i)
    }
}

fun hasFullOverlap(list1: List<Int>, list2: List<Int>): Boolean {
    return list1.containsAll(list2) or list2.containsAll(list1)
}

fun part1(input: List<List<List<Int>>>): Int {
    var numberOfFullOverlaps = 0
    input.forEach {
        if (hasFullOverlap(it[0], it[1])) {
            numberOfFullOverlaps += 1
        }
    }

    return numberOfFullOverlaps
}

fun hasPartialOverlap(list1: List<Int>, list2: List<Int>): Boolean {
    return list1.any { list2.contains(it) }
}

fun part2(input: List<List<List<Int>>>): Int {
    var numberOfPartialOverlaps = 0
    input.forEach {
        if (hasPartialOverlap(it[0], it[1])) {
            numberOfPartialOverlaps += 1
        }
    }

    return numberOfPartialOverlaps
}

fun main() {
    val input = File("input.txt")
        .readLines()
        .map { line -> line.split(",")
            .map { sectionString ->
                val sectionRange = sectionString.split("-").map { it.toInt() }
                createSectionList(sectionRange.first(), sectionRange.last()) }
        }

    println(part1(input))
    println(part2(input))
}