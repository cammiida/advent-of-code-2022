package day01

import java.io.File

fun main() {

    val input = File("input.txt").readLines()

    var maxCalories = 0
    var currentCalories = 0

    input.forEach { line ->
        if (line != "") {
            currentCalories += line.toInt()
        } else {
            if (currentCalories > maxCalories) {
                maxCalories = currentCalories
            }
            currentCalories = 0
        }
    }
    print(maxCalories)
}
