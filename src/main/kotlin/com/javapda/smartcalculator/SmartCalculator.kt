package com.javapda.smartcalculator

class SmartCalculator(val smartCalculatorVariableDictionary: SmartCalculatorVariableDictionary = vd) {
    fun somethingToTest(): Int = 34
    fun userInput(userInputGiven: String): Boolean {
        if (userInputGiven.isBlank()) return true
        val userInput=userInputGiven.trim()
        if (userInput.isInvalidUserInput()) {
            if (userInput.isValidVariableName()) {
                // need to save the calculated value
                if (userInput.isExistingVariable(smartCalculatorVariableDictionary)) {
                    println(userInput.existingVariableValue(smartCalculatorVariableDictionary))
                } else {
                    println("Unknown variable")
                }
            } else if (userInput.looksLikeACommand() && userInput.isInvalidCommand()) {
                println("Unknown command")
            } else if (userInput.looksLikeAssignment() && userInput.isInvalidEquation()) {
                println("Invalid assignment")
            } else if (hasUnbalancedParentheses(userInput)) {
                println("Invalid expression")
            } else if (userInput.isNotNumber() && userInput.isSingleToken() && userInput.isInvalidVariableName()) {
                println("Invalid identifier")
            } else if (userInput.isInvalidExpression()) {
                println("Invalid expression")
            } else if (userInput.hasNonExistentVariables(smartCalculatorVariableDictionary)) {
                println("Unknown variable")
            } else {
                throw IllegalArgumentException("ERROR: user input '$userInput' is neither a valid command nor a valid expression")
            }

            return true
        }
        when (userInput) {
            "/exit" -> return false
            "/info" -> {
                info()
                return true
            }

            in listOf("/help", "?") -> {
                help()
                return true
            }

            "" -> return true
        }
        if (userInput.isValidEquation()) {
            evaluateEquation(userInput)
        } else if (userInput.hasNonExistentVariables(smartCalculatorVariableDictionary)) {
            println("Unknown variable")
        } else if (userInput.isNumber()) {
            println(userInput)
        } else if (userInput.isExistingVariable(smartCalculatorVariableDictionary)) {
            println(userInput.existingVariableValue(smartCalculatorVariableDictionary))
        } else if (userInput.isValidExpression()) {
//            println(evaluateExpression(userInput))
            println(evaluateInfixExpression(userInput.reconcilePlusMinusSequences().trim(), smartCalculatorVariableDictionary))
        }

        return true
    }

    private fun info() {
        println(
            """
            ${smartCalculatorVariableDictionary.info()}
        """.trimIndent()
        )
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
              INPUT                                       OUTPUT
              -----                                       ------
              -2 + 4 - 5 + 6                              3
              9 +++ 10 -- 8                               27
              3 --- 5                                     -2
              14    - 12                                  12
              10 --- 5                                    5
              10 -- 5                                     15
              10 - 5                                      5
              3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)     121
        """.trimIndent()
        )
    }

}


fun main() {
    val smartCalculator = SmartCalculator()
    while (smartCalculator.userInput(readln()));
    println("Bye!")
}