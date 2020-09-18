package com.kofanity.sampleapp

import com.kofanity.filter.Kofanity
import com.kofanity.filter.PaperFilter

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Welcome to Kofanity")

            Kofanity.install(PaperFilter())

            val stringToFilter = "Testing this string"

            println("String to filter: $stringToFilter")
            println("Needs filtering: ${Kofanity.needsFiltering(stringToFilter)}")
            println("Replace: ${Kofanity.replace(stringToFilter)}")
            println("Remove: ${Kofanity.replace(stringToFilter)}")
        }
    }
}