package com.kofanity.filter

import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class Kofanity constructor() {
    init {
        throw AssertionError()
    }

    abstract class Filter(rule: Rule = Rule()) {
        @get:JvmSynthetic // Hide from public API.
        internal val explicitTag = ThreadLocal<String>()

        lateinit var badwords: Array<String>

        init {
            if (rule.isNetworkLoaded) {
                // do something
            } else {
                badwords = try {
                    this.javaClass::class.java.getResource(rule.path).readText().split("\r\n").toTypedArray()
                } catch (e: Exception) {
                    arrayListOf<String>().toTypedArray()
                }
            }
        }

        @get:JvmSynthetic // Hide from public API.
        internal open val tag: String?
            get() {
                val tag = explicitTag.get()
                if (tag != null) {
                    explicitTag.remove()
                }
                return tag
            }

        open fun remove(stringToFilter: String): String {
            return prepareFilter(stringToFilter) ?: stringToFilter
        }

        open fun replace(stringToFilter: String, replaceString: String = "*"): String {
            return prepareFilter(stringToFilter) ?: stringToFilter
        }

        open fun needsFiltering(stringToFilter: String): Boolean {
            println("Check filter $stringToFilter")
            return badwords.firstOrNull { stringToFilter.contains(it) } != null
        }

        private fun prepareFilter(message: String?, vararg args: Any?): String? {
            // Consume tag even when message is not filtered so that next message is correctly tagged.
            val tag = tag

            return filter(tag, message)
        }

        private fun filter(tag: String?, message: String?): String {
            return "Test"
        }
    }

    companion object Cleaner : Filter() {
        /** Add a new filter */
        @JvmStatic fun install(filter: Filter) {
            require(filter !== this) { "Cannot filter a filter dawg." }
            synchronized(filters) {
                filters.add(filter)
                filterArray = filters.toTypedArray()
            }
        }

        /** Adds new filters */
        @JvmStatic fun install(vararg filters: Filter) {
            for (filter in filters) {
                requireNotNull(filter) { "filters contained null" }
                require(filter !== this) { "Cannot filter a filter dawg." }
            }
            synchronized(this.filters) {
                Collections.addAll(this.filters, *filters)
                filterArray = this.filters.toTypedArray()
            }
        }

        @get:[JvmStatic JvmName("filterCount")]
        val filterCount get() = filterArray.size

        // Both fields guarded by 'filters'.
        private val filters = ArrayList<Filter>()
        @Volatile private var filterArray = emptyArray<Filter>()
    }


}