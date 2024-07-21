package com.javapda.smartcalculator

import java.math.BigInteger

class SmartCalculatorVariableDictionary : VariableProvider {
    override fun hasVariable(variableCandidate: String): Boolean =
        map.containsKey(variableCandidate)

    override fun getVariableValue(variableCandidate: String): BigInteger = map[variableCandidate]!!

    override fun put(variableName: String, number: BigInteger): Pair<String, BigInteger> {
        if (variableName.isInvalidVariableName()) {
            throw IllegalArgumentException("invalid variable name '$variableName' provided")
        }
        map[variableName] = number
        return variableName to number
    }

    private fun printVariables() =
        buildString {
            "**".repeat(20)
            map.forEach { (key, value) ->
                appendLine("$key:   $value")
            }
        }

    fun info(): String {
        return """
            ${this.javaClass.simpleName}
            No. variables:  ${map.size}
            ${if (map.isEmpty()) "no variables" else printVariables()}
        """.trimIndent()
    }

    private val map: MutableMap<String, BigInteger> = mutableMapOf()
}