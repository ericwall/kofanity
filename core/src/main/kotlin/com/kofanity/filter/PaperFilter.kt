package com.kofanity.filter

import com.kofanity.filter.Kofanity.Filter
import java.util.regex.Pattern


/** A [Filter] for very sensitive info */
open class PaperFilter : Filter() {
    private val fqcnIgnore = listOf(
        Kofanity::class.java.name,
        Kofanity.Cleaner::class.java.name,
        Filter::class.java.name,
        PaperFilter::class.java.name
    )

    override val tag: String?
        get() = super.tag ?: Throwable().stackTrace
            .first { it.className !in fqcnIgnore }
            .let(::createTag)

    protected open fun createTag(element: StackTraceElement): String? {
        var tag = element.className.substringAfterLast('.')
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag
    }

    companion object {
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
}