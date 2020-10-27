package com.kofanity.filter

import com.kofanity.filter.model.Filter
import com.kofanity.filter.model.PaperFilter
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import java.io.File
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class Kofanity constructor() {
    init {
        throw AssertionError()
    }

    companion object Cleaner : Filter() {
        private var classFilter: Filter? = null

        init {
            classFilter = PaperFilter()
        }

        /** Replaces default filter */
        @JvmStatic
        fun setFilter(filter: Filter) {
            require(filter !== this) { "Cannot filter a filter." }
            classFilter = filter
        }

        @JvmStatic
        fun setDefaultFilter() {
            classFilter = PaperFilter()
        }

        override fun remove(stringToFilter: String): String {
            return classFilter?.remove(stringToFilter) ?: stringToFilter
        }

        override fun replace(stringToFilter: String): String {
            return classFilter?.replace(stringToFilter) ?: stringToFilter
        }

        override fun needsFiltering(stringToFilter: String, throwException: Boolean): Boolean {
            return classFilter?.needsFiltering(stringToFilter, throwException) ?: false
        }
    }

}