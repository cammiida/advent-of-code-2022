package day02

import java.io.File

enum class Shapes(val points: Int) {
    A(1), // rock
    B(2), // paper
    C(3), // scissors

    X(1), // rock
    Y(2), // paper
    Z(3) // scissors
}

fun Shapes.calculatePoints(opponentValue: Shapes): Int {
    val matchPoints = when (opponentValue.points - this.points) {
        -2 -> 0
        -1 -> 6
        0 -> 3
        1 -> 0
        2 -> 6
        else -> 0
    }
    val shapePoints = this.points

    return matchPoints + shapePoints
}

fun main() {
    val input = File("input.txt")
        .readLines()
        .map { it.split(" ")
            .map { shape -> Shapes.valueOf(shape) }
        }

    var totalPoints = 0
    input.forEach { row ->
        val points = row[1].calculatePoints(row[0])
        totalPoints += points
    }

    println(totalPoints)
}