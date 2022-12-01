package day01

import java.io.File

fun MutableList<Int>.replaceMin(value: Int): MutableList<Int> {
    val minValue = this.withIndex().minBy { it.value }
    if (value > minValue.value) {
        this[minValue.index] = value
    }

    return this
}

fun main() {
    val input = File("input.txt").readLines()

    val topThreeCalories = mutableListOf(0, 0, 0)
    var currentCalories = 0
    input.forEach { line ->
        if (line != "") {
            currentCalories += line.toInt()
        } else {
            topThreeCalories.replaceMin(currentCalories)
            currentCalories = 0
        }
    }
    topThreeCalories.replaceMin(currentCalories)

    println(topThreeCalories.sum())
}