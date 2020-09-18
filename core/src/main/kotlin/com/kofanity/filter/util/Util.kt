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
}