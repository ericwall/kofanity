package com.kofanity.filter.model

import com.kofanity.filter.util.Util

data class Rule(
    val path: String = "/badwords.txt",
    val replacementChar: Char = '*',
    val removalString: String? = null
) {
    val isNetworkLoaded = Util.isValidUrl(path)
}