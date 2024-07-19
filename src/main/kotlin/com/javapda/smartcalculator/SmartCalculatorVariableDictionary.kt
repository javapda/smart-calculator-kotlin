package com.javapda.smartcalculator

class SmartCalculatorVariableDictionary : VariableProvider {
    override fun hasVariable(variableCandidate: String): Boolean =
        map.containsKey(variableCandidate)

    override fun getVariableValue(variableCandidate: String): Int = map[variableCandidate]!!

    fun put(variable: String, value: Int): Pair<String, Int> {
        if (variable.isInvalidVariableName()) {
            throw IllegalArgumentException("invalid variable name '$variable' provided")
        }
        map[variable] = value
        return variable to value
    }

    private val map: MutableMap<String, Int> = mutableMapOf()
}