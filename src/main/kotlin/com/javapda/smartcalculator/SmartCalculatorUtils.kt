package com.javapda.smartcalculator

fun <T> reqPOA(expected: T, actual: T) {
    require(expected == actual) { "expected '$expected', but found '$actual'" }
}

fun main() {
    stage1of8()
    stage2of8()
}

fun stage2of8() {
    println("implemented in SmartCalculator")
}


fun stage1of8() {
    // println(readln().split(" ").map(String::toInt).sum())
    reqPOA(13, "5 8".split(" ").map(String::toInt).sum())
}

fun Char.isPlusOrMinusOperator(): Boolean = this in "+-"
fun Char.isNotPlusOrMinusOperator(): Boolean = !isPlusOrMinusOperator()
fun Char.isMinusOperator(): Boolean = this == '-'
fun Char.isPlusOperator(): Boolean = this == '+'
fun Char.isMathOperator(): Boolean = this in "+-*/"
fun Char.isNotMathOperator(): Boolean = !isMathOperator()
fun String.isMathOperator(): Boolean = this in "+-*/"
fun String.isNotMathOperator(): Boolean = !isMathOperator()
fun String.isNumber(): Boolean = toDoubleOrNull() != null
fun String.isNotNumber(): Boolean = !isNumber()
fun Int.isEven(): Boolean = this % 2 == 0
fun Int.isOdd(): Boolean = !isEven()
fun String.isNotPlusMinusSequence(): Boolean = this.any(Char::isNotPlusOrMinusOperator)
fun String.isPlusMinusSequence(): Boolean = !isNotPlusMinusSequence()
fun String.isValidExpression(): Boolean {
    val tokens = this.split("""\s+""".toRegex())
    return tokens.all { token -> (token.isNumber() || token.isPlusMinusSequence()) }
}

fun String.isInvalidExpression(): Boolean = !isValidExpression()
val validCommands = listOf("/exit", "/help")
fun String.looksLikeACommand(): Boolean = isNotBlank() && this[0] == '/'
fun String.isValidCommand(): Boolean = looksLikeACommand() && this in validCommands
fun String.isInvalidCommand(): Boolean = !isValidCommand()
fun String.isValidUserInput(): Boolean = isValidExpression() || isValidCommand()
fun String.isInvalidUserInput(): Boolean = !isValidUserInput()

/**
 * Plus minus net operator
 * given a string of some combination of '+' and '-' characters
 * returns a '+' or '-' depending on the net of negation
 * given          returns
 * "-"            '-'
 * "--"           '+'
 * "+-+"          '-'
 * @return
 */
fun String.plusMinusNetOperator(): Char {
    return if (this.count { token -> token.isMathOperator() && token == '-' }.isOdd()) '-' else '+'
}

fun evaluateEquation(tokenString: String): Int = evaluateEquation(tokenString.split("""\s+""".toRegex()))
fun evaluateEquation(tokens: List<String>): Int {
    var result = 0
    var plusMinusSequence = ""
    tokens.forEachIndexed { idx, token ->
        if (token.isNumber()) {
            if (plusMinusSequence.plusMinusNetOperator().isMinusOperator()) {
                result -= token.toInt()
            } else {
                result += token.toInt()
            }
            plusMinusSequence = ""
        } else if (token.isPlusMinusSequence()) {
            plusMinusSequence += token
        } else {
            throw IllegalArgumentException("Unexpected not a number and not a known operator ('+','-')")
        }
    }
    return result
}
