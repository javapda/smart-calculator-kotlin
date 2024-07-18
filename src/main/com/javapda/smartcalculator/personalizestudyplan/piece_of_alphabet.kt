package com.javapda.smartcalculator.personalizestudyplan

import com.javapda.smartcalculator.reqPOA

/**
 * https://hyperskill.org/diagnostics/step/9034
 * Piece of alphabet
 * Write a program that reads a string and output true only when the letters
 * of this string form a substring of the ordered English alphabet, for
 * example, "abc", "xy", "pqrst".
 *
 * Otherwise, it should print out false.
 *
 * Note: string can consist of a single character. Assume an empty string as an
 * alphabet substring.
 *
 * Sample Input 1:
 *
 * abc
 *
 * Sample Output 1:
 *
 * true
 *
 * Sample Input 2:
 *
 * bce
 *
 * Sample Output 2:
 *
 * false
 */

fun main() {
    fun String.isPieceOfAlphabet(): Boolean = this.lowercase() in "abcdefghijklmnopqrstuvwxyz"
    println(readln().isPieceOfAlphabet())
    reqPOA(true, "abc".isPieceOfAlphabet())
    reqPOA(false, "bce".isPieceOfAlphabet())
}