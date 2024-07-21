package com.javapda.smartcalculator

import java.math.BigInteger
import java.util.*

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
fun Char.isParenthesis(): Boolean = this in "()"
fun Char.isNotParenthesis(): Boolean = !isParenthesis()
fun Char.isMathOperator(): Boolean = this in "+-*/"
fun Char.isNotMathOperator(): Boolean = !isMathOperator()
fun String.isLeftParenthesis(): Boolean = this == "("
fun String.isRightParenthesis(): Boolean = this == ")"
const val supportedMathOperators = "+-*/"
fun String.isMathOperator(): Boolean = this in supportedMathOperators
fun String.isNotMathOperator(): Boolean = !isMathOperator()
fun String.isNumber(): Boolean = toBigDecimalOrNull() != null
fun Char.isNumber(): Boolean = this in '0'..'9'
fun Char.isNotNumber(): Boolean = !isNumber()
fun Char.isPeriod(): Boolean = this == '.'
fun Char.isNotPeriod(): Boolean = !isPeriod()
fun String.isNotNumber(): Boolean = !isNumber()
fun Int.isEven(): Boolean = this % 2 == 0
fun Int.isOdd(): Boolean = !isEven()
fun String.isNotPlusMinusSequence(): Boolean = this.any(Char::isNotPlusOrMinusOperator)
fun String.isPlusMinusSequence(): Boolean = !isNotPlusMinusSequence()
fun String.isValidExpression(): Boolean {
//    val tokens = this.split("""\s+""".toRegex())
    if (hasUnbalancedParentheses(this)) return false
    if (containsTimesSequence()) return false
    if (containsDivideSequence()) return false
    val tokens = this
        .replace("*", " * ")
        .replace("/", " / ")
        .replace("-", " - ")
        .replace("+", " + ")
        .replace("(", " ( ")
        .replace(")", " ) ")
        .trim()
        .split("""\s+""".toRegex())

    return tokens.all { token -> (token.isRightParenthesis() || token.isLeftParenthesis() || token.isNumber() || token.isMathOperator() || token.isPlusMinusSequence() || token.isValidVariableName()) }
            && (!tokens.last().isMathOperator())
}

fun String.hasNonExistentVariables(varProv: VariableProvider): Boolean =
    this.split("""\s+""".toRegex()).any {
        it.isNotNumber() && it.isValidVariableName() && !varProv.hasVariable(it)
    }


interface VariableProvider {
    fun hasVariable(variableCandidate: String): Boolean
    fun getVariableValue(variableCandidate: String): BigInteger
    fun put(variableName: String, number: BigInteger): Pair<String, BigInteger>
}

fun String.isInvalidExpression(): Boolean = !isValidExpression()
val validCommands = listOf("/exit", "/help", "/info")
fun String.looksLikeACommand(): Boolean = isNotBlank() && this[0] == '/'
fun String.looksLikeAssignment(): Boolean = isNotBlank() && this.contains("=")
fun String.isValidCommand(): Boolean = looksLikeACommand() && this in validCommands
fun String.isInvalidCommand(): Boolean = !isValidCommand()
fun String.isValidUserInput(): Boolean = (looksLikeACommand() && isValidCommand()) ||
        (!looksLikeACommand() && (isValidEquation() || isValidExpression()))

fun String.isInvalidUserInput(): Boolean = !isValidUserInput()
fun String.isValidVariableName(): Boolean = "[a-zA-Z]+".toRegex().matches(this)
val vd = SmartCalculatorVariableDictionary()
fun String.isExistingVariable(varProv: VariableProvider = vd): Boolean =
    isValidVariableName() && varProv.hasVariable(this)

fun String.existingVariableValue(varProv: VariableProvider = vd): BigInteger =
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

fun String.isInvalidRHS(varProv: VariableProvider = vd): Boolean = !isValidRHS(varProv)
fun String.isSingleToken(): Boolean = this.split("""\s+""".toRegex()).count() == 1 && !this.contains("=")
fun String.containsTimesSequence(): Boolean =
    ".*[^*]*([*]{2,})[^*]*.*".toRegex().matches(this)

fun String.containsDivideSequence(): Boolean =
    ".*[^*]*([/]{2,})[^*]*.*".toRegex().matches(this)

fun String.containsTimesOrDivideSequence(): Boolean = containsTimesSequence() || containsDivideSequence()
fun String.reconcilePlusMinusSequences(): String {
    val regex = ".*[^+-]*([+-]{2,})[^+-]*.*".toRegex()
    if (!regex.matches(this)) return this
    var expr = this
    val found = regex.find(expr)
    while (regex.matches(expr)) {
        val plusMinusSequence = regex.find(expr)!!.groupValues[1]
        val replacement = plusMinusSequence.plusMinusNetOperator().toString()
        expr = expr.replace(plusMinusSequence, replacement)
    }
    return expr
}

fun String.isValidEquation(varProv: VariableProvider = vd): Boolean {
    val tokens = this.split("=").map(String::trim)
    if (tokens.size != 2) return false
    val lhs = tokens[0]
    if (lhs.isInvalidLHS()) return false

    val rhs = tokens[1]
    return rhs.isValidRHS(varProv)

}

fun String.isInvalidEquation(varProv: VariableProvider = vd): Boolean = !isValidEquation(varProv)

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

fun evaluateExpression(tokenString: String, varProv: VariableProvider = vd): BigInteger =
    evaluateExpression(tokenString.split("""\s+""".toRegex()), varProv)

fun evaluateExpression(tokens: List<String>, varProv: VariableProvider = vd): BigInteger {
    var result = BigInteger.ZERO
    var plusMinusSequence = ""
    fun adjustResult(num: BigInteger) {
        if (plusMinusSequence.plusMinusNetOperator().isMinusOperator()) {
            result -= num
        } else {
            result += num
        }
        plusMinusSequence = ""

    }
    tokens.forEachIndexed { idx, token ->
        if (token.isValidVariableName()) {
            val value = token.existingVariableValue(varProv)
            adjustResult(value)
        } else if (token.isNumber()) {
            adjustResult(token.toBigInteger())
        } else if (token.isPlusMinusSequence()) {
            plusMinusSequence += token
        } else {
            throw IllegalArgumentException("Unexpected not a number and not a known operator ('+','-')")
        }
    }
    return result
}

fun evaluateEquation(tokenString: String, varProv: VariableProvider = vd) {
    val (lhs, rhs) = tokenString.split("=").map(String::trim)
    if (lhs.isInvalidLHS()) throw IllegalArgumentException("invalid LHS '$lhs'")
    if (rhs.isInvalidRHS(varProv)) throw IllegalArgumentException("invalid RHS '$rhs'")
    varProv.put(lhs, evaluateExpression(rhs, varProv))
}

fun hasBalancedParentheses(infixExpression: String): Boolean = !hasUnbalancedParentheses(infixExpression)
fun hasUnbalancedParentheses(infixExpression: String): Boolean {
    var leftParentheses = 0
    var rightParentheses = 0
    infixExpression.forEach { char ->
        if (char == '(') leftParentheses++
        else if (char == ')') rightParentheses++
        if (rightParentheses > leftParentheses) return true
    }
    return leftParentheses != rightParentheses
}

fun convertInfixExpressionToTokens(infixExpression: String): List<String> {
    if (hasUnbalancedParentheses(infixExpression))
        throw IllegalArgumentException("unbalanced parentheses in infix expression '$infixExpression'")
    var numBuffer: String = ""
    var variableBuffer: String = ""
    val tokens = mutableListOf<String>()
    fun consumeNumBuffer() {
        if (numBuffer.isNotEmpty()) {
            tokens.add(numBuffer)
            numBuffer = ""
        }
    }

    fun consumeVariableBuffer() {
        if (variableBuffer.isNotEmpty()) {
            tokens.add(variableBuffer)
            variableBuffer = ""
        }
    }
    infixExpression.replace("""\s+""".toRegex(), "").forEach { char ->
        with(char) {
            if (isMathOperator() || isParenthesis()) {
                consumeNumBuffer()
                consumeVariableBuffer()
                tokens.add(char.toString())
            } else if (isNumber()) {
                consumeVariableBuffer()
                numBuffer += char
            } else if (isLetter()) {
                consumeNumBuffer()
                variableBuffer += char
            } else if (isPeriod()) {
                if (numBuffer.isNotEmpty()) {
                    numBuffer += char
                } else if (variableBuffer.isNotEmpty()) {
                    variableBuffer += char
                } else {
                    throw IllegalArgumentException("floating point numbers must be represented with a number immediately before the decimal point")
                }
            }
        }


    }
    consumeNumBuffer()
    consumeVariableBuffer()

    return tokens
}

fun isHigherMathOperatorPrecedence(leftOperator: String, rightOperator: String): Boolean {
    return supportedMathOperators.indexOf(leftOperator) > supportedMathOperators.indexOf(rightOperator)
}

fun evaluateInfixExpression(infixExpression: String, varProv: VariableProvider = vd, verbose: Boolean = true): BigInteger {
    return evaluatePostfixExpression(convertInfixToPostfix(infixExpression), varProv, verbose)
}

fun evaluatePostfixExpression(postfixExpression: String, varProv: VariableProvider = vd, verbose: Boolean = true): BigInteger {
    val stack = Stack<String>()
    val tokens = postfixExpression.split("""\s+""".toRegex())
    fun toNumber(operand: String): BigInteger {
        return if (operand.isNumber()) operand.toBigInteger() else operand.existingVariableValue(varProv)
    }

    tokens.forEach { token ->
        with(token) {
            if (isNumber() || isValidVariableName()) {
                // If the incoming element is a number, push it into the stack (the whole number, not a single digit!).
                stack.push(token)
            } else if (isMathOperator()) {
                val operand2 = toNumber(stack.pop())
                val operand1 = toNumber(stack.pop())
                if (token == "+") {
                    stack.push((operand1 + operand2).toString())
                } else if (token == "-") {
                    stack.push((operand1 - operand2).toString())
                } else if (token == "*") {
                    stack.push((operand1 * operand2).toString())
                } else if (token == "/") {
                    if (operand2 == BigInteger.ZERO) {
                        throw IllegalArgumentException("evaluatePostfixExpression : ZERO not allowed in DENOMINATOR")
                    }
                    stack.push((operand1 / operand2).toString())
                }

            } else {
                throw IllegalStateException("evaluatePostfixExpression : unhandled math operator '$token', from postfixExpression=$postfixExpression")
            }
        }
    }
    val result = stack.pop()
    return if (result.isNumber()) result.toBigInteger() else
        throw IllegalArgumentException("evaluatePostfixExpression: result='$result' is not a number, ")
}

fun convertInfixToPostfix(infixExpression: String, verbose: Boolean = false): String {
    val stack = Stack<String>()
    if (hasUnbalancedParentheses(infixExpression))
        throw IllegalArgumentException("unbalanced parentheses in infix expression '$infixExpression'")
    val tokens = convertInfixExpressionToTokens(infixExpression) //infixExpression.split("""\s+""")
    val result = mutableListOf<String>()
    tokens.forEach { token ->
        with(token) {
            if (isNumber() || isValidVariableName()) {
                // Add operands (numbers and variables) to the result (postfix notation) as they arrive.
                result.add(token)
            } else if (stack.isEmpty() || stack.peek().isLeftParenthesis()) {
                // If the stack is empty or contains a left parenthesis on top, push the incoming operator on the stack.
                stack.push(token)
            } else if (isMathOperator() && stack.peek().isMathOperator() && isHigherMathOperatorPrecedence(
                    token,
                    stack.peek()
                )
            ) {
                // If the incoming operator has higher precedence than the top of the stack, push it on the stack.
                stack.push(token)
            } else if (isMathOperator() && stack.peek().isMathOperator() && !isHigherMathOperatorPrecedence(
                    token,
                    stack.peek()
                )
            ) {
                // If the precedence of the incoming operator is lower than or equal to that of the top of the stack,
                // pop the stack and add operators to the result until you see an operator that has smaller precedence
                // or a left parenthesis on the top of the stack; then add the incoming operator to the stack.
                while (stack.isNotEmpty() && (stack.peek().isMathOperator() && !isHigherMathOperatorPrecedence(
                        token,
                        stack.peek()
                    ) || !stack.peek().isLeftParenthesis())
                ) {
                    val poppedToken = stack.pop()
                    if (poppedToken.isMathOperator()) {
                        result.add(poppedToken)
                    }
                    // otherwise we throw it away
                }
                stack.push(token)

            } else if (token.isLeftParenthesis()) {
                // If the incoming element is a left parenthesis, push it on the stack.
                stack.push(token)
            } else if (token.isRightParenthesis()) {
                // If the incoming element is a right parenthesis, pop the stack and add operators to the result
                // until you see a left parenthesis. Discard the pair of parentheses.
                while (stack.isNotEmpty()) {
                    val poppedToken = stack.pop()
                    if (!poppedToken.isLeftParenthesis()) {
                        result.add(poppedToken)
                    } else {
                        break
                    }
                }
            } else {
                // nothing?
                throw IllegalStateException("DID NOTHING WITH token $token")
            }
        }

    }
    while (stack.isNotEmpty()) {
        result.add(stack.pop())
    }
    if (verbose) {
        println(
            """
        convertInfixToPostfix
        infixExpression:   $infixExpression
        result:            $result
        result as text:    ${result.joinToString(" ")}
    """.trimIndent()
        )
    }

    return result.joinToString(" ") // "3 2 4 * +"
}

