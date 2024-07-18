package com.javapda.smartcalculator

class SmartCalculator {
    fun somethingToTest(): Int = 34
    fun userInput(userInput: String): Boolean {
        if (userInput.isBlank()) return true
        if (userInput.isInvalidUserInput()) {
            if (userInput.looksLikeACommand() && userInput.isInvalidCommand()) {
                println("Unknown command")
            } else if (userInput.isInvalidExpression()) {
                println("Invalid expression")
            } else {
                throw IllegalArgumentException("ERROR: user input '$userInput' is neither a valid command nor a valid expression")
            }
            return true
        }
        when (userInput) {
            "/exit" -> return false
            in listOf("/help", "?") -> {
                help()
                return true
            }

            "" -> return true
        }
        if (userInput.isNotBlank()) {
            println(evaluateEquation(userInput))
        }

        return true
    }

    private fun help() {
        println("The program calculates the sum of numbers")
        println(
            """
            Welcome to the Smart Calculator (TM) javapda (C) all rights, reserved - just kidding :-)
            commands:
              /help       : show this help
              /exit       : exit the program
            examples:
              INPUT                 OUTPUT
              -----                 ------
              -2 + 4 - 5 + 6        3
              9 +++ 10 -- 8         27
              3 --- 5               -2
              14    - 12            12
              10 --- 5              5
              10 -- 5               15
              10 - 5                5
        """.trimIndent()
        )
    }

}


fun main() {
    val smartCalculator = SmartCalculator()
    while (smartCalculator.userInput(readln()));
    println("Bye!")
}