package com.kofanity.filter.model

import com.kofanity.filter.util.Util

abstract class Filter(val rule: Rule = Rule()) {
    private val badwords: Map<String, String>

    init {
        if (rule.isNetworkLoaded) {
            throw UnsupportedOperationException("Feature not implemented yet")
        } else {
            badwords = try {
                this.javaClass::class.java.getResource(rule.path).readText().split("\r\n", "\n").map { badWord ->
                    badWord to Util.replaceAll(badWord, rule.replacementChar)
                }.toMap()
            } catch (e: Exception) {
                mapOf<String, String>()
            }
        }
    }

    open fun remove(stringToFilter: String): String {
        return prepareFilter(stringToFilter, true)
    }

    open fun replace(stringToFilter: String): String {
        return prepareFilter(stringToFilter)
    }

    open fun needsFiltering(stringToFilter: String, throwException: Boolean = false): Boolean {
        val containsBadWord = stringToFilter.split(" ").firstOrNull {
            badwords.containsKey(it)
        } != null

        if (throwException && containsBadWord) throw Exception("Bad word exception")
        else return containsBadWord
    }

    private fun prepareFilter(message: String, remove: Boolean = false): String {
        return filter(message, remove)
    }

    private fun filter(message: String, remove: Boolean): String {
        return message.split(" ").map { word ->
            if (badwords.containsKey(word)) {
                if (remove) rule.removalString
                else badwords[word]
            } else word
        }.filterNotNull().joinToString(" ")
    }
}