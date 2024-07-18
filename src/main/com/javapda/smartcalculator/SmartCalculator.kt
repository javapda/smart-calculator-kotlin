package com.javapda.smartcalculator

class SmartCalculator {
    fun userInput(userInput: String): Boolean {
        if (userInput == "/exit") return false
        if (userInput.isBlank()) {
//            println("it was blank")
        } else {
            processNumbers(userInput.split("""\s+""".toRegex()).map(String::toInt))
        }

        return true
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