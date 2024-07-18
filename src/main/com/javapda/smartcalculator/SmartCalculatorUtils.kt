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
