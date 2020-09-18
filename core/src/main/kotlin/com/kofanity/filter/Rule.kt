package com.kofanity.filter

import com.kofanity.filter.util.Util

data class Rule(
    val path: String = "/badwords.txt"
) {
    val isNetworkLoaded = Util.isValidUrl(path)
}