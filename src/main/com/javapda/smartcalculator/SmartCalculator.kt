package com.javapda.smartcalculator

class SmartCalculator {
    fun userInput(userInput: String): Boolean {
        when (userInput) {
            "/exit" -> return false
            "/help" -> {
                help()
                return true
            }
            "" -> return true
        }
        if (userInput.isNotBlank()) {
            processNumbers(userInput.split("""\s+""".toRegex()).map(String::toInt))
        }

        return true
    }

    private fun help() {
        println("The program calculates the sum of numbers")
    }

    private fun processNumbers(numbers: List<Int>) {
        println(numbers.sum())
    }
}

fun main() {
    val smartCalculator = SmartCalculator()
    while (smartCalculator.userInput(readln()));
    println("Bye!")
}