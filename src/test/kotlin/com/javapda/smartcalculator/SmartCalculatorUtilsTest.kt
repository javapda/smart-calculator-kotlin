package com.javapda.smartcalculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * Smart calculator utils test
 * Tests for functions in SmartCalculatorUtils
 *
 * @constructor Create empty Smart calculator utils test
 */
class SmartCalculatorUtilsTest {

    lateinit var varDict: SmartCalculatorVariableDictionary

    @BeforeEach
    fun setUp() {
        varDict = SmartCalculatorVariableDictionary()
        varDict.put("Atu", 42) // Atu = Answer to the universe
        varDict.put("a", 4) // https://hyperskill.org/projects/88/stages/492/implement
        varDict.put("b", 5) // https://hyperskill.org/projects/88/stages/492/implement
        varDict.put("c", 6) // https://hyperskill.org/projects/88/stages/492/implement
    }

    @ParameterizedTest
    @MethodSource("argsWithPlusMinusSequences")
    fun testReconcilePlusMinusSequences(testData: Pair<String, String>) {
        val (unreconciledArgWithPlusMinusSequences, expectedReconciled) = testData
        assertEquals(expectedReconciled, unreconciledArgWithPlusMinusSequences.reconcilePlusMinusSequences())
    }

    @ParameterizedTest
    @ValueSource(strings=["***", """//""", "*** front-loaded","rear-loaded ***"])
    fun testContainsTimesOrDivideSequence(testData:String) {
        assertTrue(testData.containsTimesOrDivideSequence())
    }

    @Test
    fun testRegexReplacement() {
        assertEquals("a + b", "a +++ b".replace("""[+]+""".toRegex(), "+"))
        assertEquals("a - b", "a --- b".replace("""[-]+""".toRegex(), "-"))
    }

    @Test
    fun testRegexGeneral() {
        var expr = "some +++ other --- more -+-+-"
        val regex = ".*[^+-]*([+-]{2,})[^+-]*.*".toRegex()
        assertTrue(".*([+-]+).*".toRegex().matches(expr))
        assertTrue(regex.matches(expr))
        val found = regex.find(expr)
        while (regex.matches(expr)) {
            val plusMinusSequence = regex.find(expr)!!.groupValues[1]
            val replacement = plusMinusSequence.plusMinusNetOperator().toString()
            expr = expr.replace(plusMinusSequence, replacement)
        }
        println(
            """
            RESULT, expr=$expr
        """.trimIndent()
        )
    }

    @Test
    fun testIsHigherMathOperatorPrecedence() {
        assertTrue(isHigherMathOperatorPrecedence("*", "+"))
        assertTrue(isHigherMathOperatorPrecedence("/", "+"))
        assertTrue(isHigherMathOperatorPrecedence("*", "-"))
        assertTrue(isHigherMathOperatorPrecedence("/", "-"))
    }

    @Test
    fun testConvertInfixExpressionToTokens() {
        assertEquals(listOf("(", "3", "+", "4", ")"), convertInfixExpressionToTokens("(3 + 4)"))
        assertEquals(
            listOf("5", "*", "(", "67", "/", "803", ")", "+", "ABC"),
            convertInfixExpressionToTokens("5 * (67 / 803) + ABC")
        )

        // we get an exception if unbalanced parentheses
        assertThrows(IllegalArgumentException::class.java) {
            convertInfixExpressionToTokens("5 * (67 / 803)) + ABC")
        }
        assertThrows(IllegalArgumentException::class.java) {
            convertInfixExpressionToTokens("(5 * (67 / 803)) + ABC)")
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["(3+4)", "2 * (3 + 4) + 1"])
    fun testHasBalancedParentheses(infixExpression: String) {
        assertTrue(hasBalancedParentheses(infixExpression))
    }

    @ParameterizedTest
    @ValueSource(strings = ["(3+4))", "(2 * (3 + 4) + 1"])
    fun testHasUnbalancedParentheses(infixExpression: String) {
        assertTrue(hasUnbalancedParentheses(infixExpression))
    }

    @ParameterizedTest
    @MethodSource("infixExpressionsToEvaluatedOutcomes")
    fun testEvaluateInfixExpression(testData: Pair<String, Int>) {
        val (infixExpression, expectedOutcome) = testData
        assertEquals(expectedOutcome, evaluateInfixExpression(infixExpression, varDict))
    }

    @ParameterizedTest
    @MethodSource("postfixExpressionsToEvaluatedOutcomes")
    fun testEvaluatePostfixExpression(testData: Pair<String, Int>) {
        val (postfixExpression, expectedPostfixOutcome) = testData
        assertEquals(expectedPostfixOutcome, evaluatePostfixExpression(postfixExpression, varDict))
    }

    @ParameterizedTest
    @MethodSource("infixExpressionsToExpectedPostfixOutcomes")
    fun testConvertInfixToPostfix(testData: Pair<String, String>) {
        val (infixExpression, expectedPostfixNotation) = testData
        assertEquals(expectedPostfixNotation, convertInfixToPostfix(infixExpression))
    }

    companion object {
        @JvmStatic
        fun argsWithPlusMinusSequences(): List<Pair<String, String>> {
            return listOf(
                "some +++ other --- more -+-+-" to "some + other - more -",
                "2 + 2" to "2 + 2",
                "2 +++ 2" to "2 + 2",
                "2 - 2" to "2 - 2",
                "2 - 2" to "2 - 2",
                "2 -- 2" to "2 + 2",
                "2 --- 2" to "2 - 2",
            )
        }

        @JvmStatic
        fun infixExpressionsToEvaluatedOutcomes(): List<Pair<String, Int>> {
            // NOTE: dictionary variables (Atu=42, a=4, b=5, c=6) should have been defined by this point
            return listOf(
                "3 - 2" to 1,
                "Atu 2 -" to 40,
                "8 * 3 + 12 * (4 - 2)" to 48,
                "2 - 2 + 3" to 3,
                "a*2+b*3+c*(2+3)" to 53,
            )
        }

        @JvmStatic
        fun postfixExpressionsToEvaluatedOutcomes(): List<Pair<String, Int>> {
            // NOTE: dictionary variables (Atu=42) should have been defined by this point
            return listOf(
                "3 2 -" to 1,
                "8 3 * 12 4 2 - * +" to 48,
                "3 2 +" to 5,
                "Atu 2 +" to 44,
                "3 2 4 * +" to 11,
            )
        }

        @JvmStatic
        fun infixExpressionsToExpectedPostfixOutcomes(): List<Pair<String, String>> {
            return listOf(
                "8 * 3 + 12 * (4 - 2)" to "8 3 * 12 4 2 - * +",
                "ABC + 5" to "ABC 5 +",
                "(ABC + 5)" to "ABC 5 +",
                "3 + 2 * 4" to "3 2 4 * +",
                "683 + 2 * 4" to "683 2 4 * +",
                "(3 + 2 * 4)" to "3 2 4 * +",
                "2 * (3 + 4) + 1" to "2 3 4 + * 1 +",
                "2 * (3 + AB) + 1" to "2 3 AB + * 1 +",
                "3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)" to "3 8 4 3 + 2 * 1 + * + 6 2 1 + / -",
            )
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["a = 3 + 7", "b = 65 ++ 86 - PreV"])
    fun testStringIsValidEquation(equationCandidate: String) {
        // pre-populate dictionary
        varDict.put("PreV", 987)
        assertTrue(equationCandidate.isValidEquation(varDict))
    }

    @ParameterizedTest
    @ValueSource(strings = ["3 + 7", "85 + JedA"])
    fun testStringIsValidRHS(rhsCandidate: String) {
        varDict.put("JedA", 243)
        assertTrue(rhsCandidate.isValidRHS(varDict))
    }


    @ParameterizedTest
    @ValueSource(strings = ["a", "nn"])
    fun testStringIsValidLHS(variableName: String) {
        assertTrue(variableName.isValidLHS())
    }

    @ParameterizedTest
    @ValueSource(strings = ["2nn"])
    fun StringIsInvalidLHS(variableName: String) {
        assertTrue(variableName.isInvalidLHS())
    }


    @ParameterizedTest
    @ValueSource(strings = ["n", "m", "a", "v", "count", "abc"])
    fun testStringIsValidVariableName(variableName: String) {
        assertTrue(variableName.isValidVariableName())
    }

    @ParameterizedTest
    @ValueSource(strings = ["a2a", "n22"])
    fun testStringIsInvalidVariableName(variableName: String) {
        assertTrue(variableName.isInvalidVariableName())
    }

    @ParameterizedTest
    @ValueSource(strings = ["/go", "/bogus", "/exit"])
    fun testStringLooksLikeACommand(userInputCandidate: String) {
        assertTrue(userInputCandidate.looksLikeACommand())
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["n = 5 + 9", "x = 5 + 45",
            "/exit", "/help", "3 + 7", "5 --- 2 ++++++ 4 -- 2 ---- 1"]
    )
    fun testStringIsValidUserInput(userInputCandidate: String) {
        assertTrue(userInputCandidate.isValidUserInput())
    }

    @ParameterizedTest
    @ValueSource(strings = ["/go", /* "Hello Friend", */ "123+"])
    fun testStringIsInvalidUserInput(userInputCandidate: String) {
        println(
            """
            userInputCandidate:   $userInputCandidate
            looksLikeACommand():  ${userInputCandidate.looksLikeACommand()}
            isValidCommand():     ${userInputCandidate.isValidCommand()}
            looksLikeACommand() && isValidCommand():  ${userInputCandidate.looksLikeACommand() && userInputCandidate.isValidCommand()}
            isInvalidUserInput:   ${userInputCandidate.isInvalidUserInput()}
        """.trimIndent()
        )
        assertTrue(userInputCandidate.isInvalidUserInput())
    }

    @ParameterizedTest
    @ValueSource(strings = ["/exit", "/help"])
    fun testStringIsValidCommand(commandCandidate: String) {
        assertTrue(commandCandidate.isValidCommand())
    }

    @ParameterizedTest
    @ValueSource(strings = ["/go"])
    fun testStringIsInvalidCommand(commandCandidate: String) {
        assertTrue(commandCandidate.isInvalidCommand())
    }


    @ParameterizedTest
    @ValueSource(strings = ["3 + 5", "+15", "8 * 3 + 12 * (4 - 2)", "abc"])
    fun testStringIsValidExpression(expressionCandidate: String) {
        assertTrue(expressionCandidate.isValidExpression())
    }

    @ParameterizedTest
    @ValueSource(strings = ["123+", "22-", "23***", "////45"])
    fun testStringIsInvalidExpression(expressionCandidate: String) {
        assertTrue(expressionCandidate.isInvalidExpression())
    }

    @Test
    fun testEvaluateEquation() {
        varDict.put("MyVarTestEvaluateEquation", 37)
        println(varDict.info())
        evaluateEquation("n = 9 + MyVarTestEvaluateEquation", varDict)
        assertTrue("n".isExistingVariable(varDict))

    }

    @Test
    fun testEvaluateExpression() {
        varDict.put("MyVarTEE", 37)
        println(varDict.info())
        assertEquals(46, evaluateExpression("9 + MyVarTEE", varDict))

        assertEquals(43, evaluateExpression("3 + 4 - 17 + 37 ---- 16"))
        assertEquals(5, evaluateExpression("10 --- 5"))
        assertEquals(15, evaluateExpression("10 -- 5"))
        assertEquals(5, evaluateExpression("10 - 5  "))

        assertEquals(-3, evaluateExpression("-3".split("""\s+""".toRegex())))
        assertEquals(1, evaluateExpression("-3 + 4".split("""\s+""".toRegex())))
        assertEquals(3, evaluateExpression("-2 + 4 - 5 + 6".split("""\s+""".toRegex())))
        assertEquals(27, evaluateExpression("9 +++ 10 -- 8".split("""\s+""".toRegex())))
        assertEquals(-2, evaluateExpression(" 3 --- 5".split("""\s+""".toRegex())))
        assertEquals(2, evaluateExpression("14       -   12".split("""\s+""".toRegex())))
    }

    @Test
    fun testCharIsMathOperator() {
        assertTrue('+'.isMathOperator())
        assertTrue('-'.isMathOperator())
        assertTrue('*'.isMathOperator())
        assertTrue('/'.isMathOperator())
    }

    @Test
    fun testCharIsNotMathOperator() {
        assertTrue('a'.isNotMathOperator())
        assertTrue('p'.isNotMathOperator())
        assertTrue('X'.isNotMathOperator())
        assertTrue('C'.isNotMathOperator())
    }

    @Test
    fun testCharIsPlusOperator() {
        assertTrue('+'.isPlusOperator())
        assertFalse('-'.isPlusOperator())
    }

    @Test
    fun testCharIsMinusOperator() {
        assertTrue('-'.isMinusOperator())
        assertFalse('+'.isMinusOperator())
    }

    @ParameterizedTest
    @ValueSource(strings = ["-", "---", "- + - - +", "++++++-++"])
    fun testStringPlusMinusNetOperatorIsMinus(plusMinusSequence: String) {
        assertEquals('-', plusMinusSequence.plusMinusNetOperator())
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "+", "--"])
    fun testStringPlusMinusNetOperatorIsPlus(plusMinusSequence: String) {
        assertEquals('+', plusMinusSequence.plusMinusNetOperator())
    }

    @ParameterizedTest
    @ValueSource(chars = ['+', '-'])
    fun testStringIsPlusOrMinusOperator(plusMinusChar: Char) {
        assertTrue(plusMinusChar.isPlusOrMinusOperator())
    }

    @ParameterizedTest
    @ValueSource(strings = ["+", "-", "*", "/"])
    fun testStringIsMathOperator(operatorText: String) {
        assertTrue(operatorText.isMathOperator())
    }

    @ParameterizedTest
    @ValueSource(strings = ["A", "P", "plus", "minus"])
    fun testStringIsNotMathOperator(operatorText: String) {
        assertTrue(operatorText.isNotMathOperator())
    }

    @ParameterizedTest
    @ValueSource(strings = ["23", "-4"])
    fun testStringIsNumber(numText: String) {
        assertTrue(numText.isNumber())
    }

    @ParameterizedTest
    @ValueSource(strings = ["2a", "ABC"])
    fun testStringIsNotNumber(numText: String) {
        assertTrue(numText.isNotNumber())
    }

    @ParameterizedTest
    @ValueSource(chars = ['0', '9'])
    fun testCharIsNumber(numText: Char) {
        assertTrue(numText.isNumber())
    }

    @ParameterizedTest
    @ValueSource(chars = ['a', '@'])
    fun testCharIsNotNumber(numText: Char) {
        assertTrue(numText.isNotNumber())
    }

    @ParameterizedTest
    @ValueSource(chars = ['(', ')'])
    fun testCharIsParenthesis(numText: Char) {
        assertTrue(numText.isParenthesis())
    }

    @ParameterizedTest
    @ValueSource(chars = ['X', '.'])
    fun testCharIsNotParenthesis(numText: Char) {
        assertTrue(numText.isNotParenthesis())
    }
}

