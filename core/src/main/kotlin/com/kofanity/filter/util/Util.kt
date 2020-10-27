package com.kofanity.filter.util


import java.net.URL


object Util {

    fun isValidUrl(url: String?): Boolean {
        return try {
            URL(url).toURI()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun replaceAll(word: String, replacementChar: Char = '*'): String {
        return word.replaceRange(0, word.length, replacementChar.toString().repeat(word.length))
    }
}