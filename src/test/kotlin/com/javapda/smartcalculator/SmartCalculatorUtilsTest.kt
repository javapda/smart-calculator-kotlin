package com.javapda.smartcalculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
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
    @ValueSource(strings = ["n", "m", "a", "v", "count"])
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
        strings = ["n = 5 + 9","x = 5 + 45",
            "/exit", "/help", "3 + 7", "5 --- 2 ++++++ 4 -- 2 ---- 1"]
    )
    fun testStringIsValidUserInput(userInputCandidate: String) {
        assertTrue(userInputCandidate.isValidUserInput())
    }

    @ParameterizedTest
    @ValueSource(strings = ["/go", "Hello Friend", "123+"])
    fun testStringIsInvalidUserInput(userInputCandidate: String) {
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
    @ValueSource(strings = ["3 + 5", "+15"])
    fun testStringIsValidExpression(expressionCandidate: String) {
        assertTrue(expressionCandidate.isValidExpression())
    }

    @ParameterizedTest
    @ValueSource(strings = ["abc", "123+", "22-"])
    fun testStringIsInvalidExpression(expressionCandidate: String) {
        assertTrue(expressionCandidate.isInvalidExpression())
    }

    @Test
    fun testEvaluateEquation() {
        assertEquals(43, evaluateEquation("3 + 4 - 17 + 37 ---- 16"))
        assertEquals(5, evaluateEquation("10 --- 5"))
        assertEquals(15, evaluateEquation("10 -- 5"))
        assertEquals(5, evaluateEquation("10 - 5  "))

        assertEquals(-3, evaluateEquation("-3".split("""\s+""".toRegex())))
        assertEquals(1, evaluateEquation("-3 + 4".split("""\s+""".toRegex())))
        assertEquals(3, evaluateEquation("-2 + 4 - 5 + 6".split("""\s+""".toRegex())))
        assertEquals(27, evaluateEquation("9 +++ 10 -- 8".split("""\s+""".toRegex())))
        assertEquals(-2, evaluateEquation(" 3 --- 5".split("""\s+""".toRegex())))
        assertEquals(2, evaluateEquation("14       -   12".split("""\s+""".toRegex())))
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
}

