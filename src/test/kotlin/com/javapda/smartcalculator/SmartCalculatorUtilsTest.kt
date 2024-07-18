package com.javapda.smartcalculator

import org.junit.jupiter.api.Assertions.*
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