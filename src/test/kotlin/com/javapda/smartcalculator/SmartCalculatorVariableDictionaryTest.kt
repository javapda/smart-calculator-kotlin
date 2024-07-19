package com.javapda.smartcalculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SmartCalculatorVariableDictionaryTest {
    lateinit var varDict: SmartCalculatorVariableDictionary

    @BeforeEach
    fun setUp() {
        varDict = SmartCalculatorVariableDictionary()
    }

    @Test
    fun existingVariableValue() {
        val variableName = "myVar"
        val variableValue = 998
        varDict.put(variableName, variableValue)
        assertEquals(variableValue, variableName.existingVariableValue(varDict))

    }

    @Test
    fun existingVariableValueForNonExistentVariable() {
        assertThrows(IllegalAccessException::class.java) {
            "ValidVarNameHere".existingVariableValue(varDict)
        }
    }

    @Test
    fun hasVariable() {
        val variableName = "myVar"
        varDict.put(variableName, 234)
        assertTrue(varDict.hasVariable(variableName))
    }

    @Test
    fun testBadVariableName() {
        assertThrows(IllegalArgumentException::class.java) {
            varDict.put("BoguS321Variable", 654)
        }

    }
}