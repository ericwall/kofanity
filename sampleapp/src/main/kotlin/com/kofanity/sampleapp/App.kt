package com.kofanity.sampleapp

import com.kofanity.filter.Kofanity
import com.kofanity.filter.model.Rule
import com.kofanity.filter.model.PaperFilter
import java.lang.Exception

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Welcome to Kofanity")
            val stringToFilter = "Testing this string asdfbadwordasdf"

            // Default Filtering Behavior
            println("String to filter: $stringToFilter")
            println("Needs filtering: ${Kofanity.needsFiltering(stringToFilter, false)}")
            println("Replace: ${Kofanity.replace(stringToFilter)}")
            println("Remove: ${Kofanity.remove(stringToFilter)}")

            Kofanity.setFilter(PaperFilter(rule = Rule(replacementChar = 'x', removalString = "<REDACTED>")))
            println("Replace with new filter: ${Kofanity.replace(stringToFilter)}")
            println("Remove with new filter: ${Kofanity.remove(stringToFilter)}")

            try {
                println("Needs Filtering Throw exception when badword is found")
                Kofanity.needsFiltering(stringToFilter, true)
            } catch (e: Exception) {
                println("Exception thrown: ${e.message}")
            }
        }
    }
}