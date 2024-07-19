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

interface VariableProvider {
    fun hasVariable(variableCandidate: String): Boolean
    fun getVariableValue(variableCandidate: String): Int
}

fun String.isInvalidExpression(): Boolean = !isValidExpression()
val validCommands = listOf("/exit", "/help")
fun String.looksLikeACommand(): Boolean = isNotBlank() && this[0] == '/'
fun String.isValidCommand(): Boolean = looksLikeACommand() && this in validCommands
fun String.isInvalidCommand(): Boolean = !isValidCommand()
fun String.isValidUserInput(): Boolean = isValidEquation() || isValidExpression() || isValidCommand()
fun String.isInvalidUserInput(): Boolean = !isValidUserInput()
fun String.isValidVariableName(): Boolean = "[a-zA-Z]+".toRegex().matches(this)
val vd = SmartCalculatorVariableDictionary()
fun String.isExistingVariable(varProv: VariableProvider = vd): Boolean =
    isValidVariableName() && varProv.hasVariable(this)

fun String.existingVariableValue(varProv: VariableProvider = vd): Int =
    if (isValidVariableName() && varProv.hasVariable(this))
        varProv.getVariableValue(this) else throw IllegalAccessException("variable '$this' does not exist in variable provider")

fun String.isInvalidVariableName(): Boolean = !isValidVariableName()
fun String.isValidLHS(): Boolean = isValidVariableName()
fun String.isInvalidLHS(): Boolean = !isValidLHS()
fun String.isValidRHS(varProv: VariableProvider = vd): Boolean =
    this.split("""\s+""".toRegex()).all { token ->
        (token.isPlusMinusSequence() || token.isNumber() ||
                (token.isValidVariableName() && token.isExistingVariable(varProv)))
    }

fun String.isValidEquation(varProv: VariableProvider = vd): Boolean {
    val tokens = this.split("=").map(String::trim)
    if (tokens.size != 2) return false
    val lhs = tokens[0]
    if (lhs.isInvalidLHS()) return false

    val rhs = tokens[1]
    return rhs.isValidRHS(varProv)

}

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
