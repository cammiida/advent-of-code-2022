package day02

import java.io.File

enum class DesiredOutcome {
    X, // lose
    Y, // draw
    Z // win
}

fun Shapes.getShapeToPlay(desiredOutcome: DesiredOutcome): Shapes {
    return when(desiredOutcome) {
        DesiredOutcome.X -> when(this) {
            Shapes.A -> Shapes.Z
            Shapes.B -> Shapes.X
            else -> Shapes.Y
        }
        DesiredOutcome.Y -> when(this) {
            Shapes.A -> Shapes.X
            Shapes.B -> Shapes.Y
            else -> Shapes.Z
        }
        else -> when(this) {
            Shapes.A -> Shapes.Y
            Shapes.B -> Shapes.Z
            else -> Shapes.X
        }
    }
}

fun main() {
    val input = File("input.txt")
        .readLines()
        .map { it.split(" ") }

    var totalPoints = 0
    input.forEach { row ->
        val inputShape = Shapes.valueOf(row[0])
        val desiredOutcome = DesiredOutcome.valueOf(row[1])
        val outputShape = inputShape.getShapeToPlay(desiredOutcome)

        totalPoints += outputShape.calculatePoints(inputShape)
    }

    println(totalPoints)
}