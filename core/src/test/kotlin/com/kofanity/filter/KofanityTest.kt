package com.kofanity.filter

import com.kofanity.filter.model.PaperFilter
import com.kofanity.filter.model.Rule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class KofanityTest {
    val badwordsTestString = "Welcome to taco town!"
    val badwords2TestString = "Welcome to burrito town!"

    @Before
    fun reset() {
        Kofanity.setDefaultFilter()
    }

    @Test
    fun `default filter checks if word needs to be filtered`() {
        val resultShouldNeedFiltering = Kofanity.needsFiltering(badwordsTestString)
        val resultShouldNotNeedFilter = Kofanity.needsFiltering(badwords2TestString)

        assertEquals(true, resultShouldNeedFiltering)
        assertEquals(false, resultShouldNotNeedFilter)
    }

    @Test
    fun `filter throws exception when parameter is passed`() {
        var message = ""
        try {
            Kofanity.needsFiltering(badwordsTestString, true)
        } catch (e: Exception) {
            message = e.message ?: ""
        }

        assertEquals("Bad word exception", message)
    }

    @Test
    fun `default filter replaces word`() {
        val resultShouldReplace = Kofanity.replace(badwordsTestString)
        val resultShouldNotReplace = Kofanity.replace(badwords2TestString)

        assertEquals("Welcome to **** town!", resultShouldReplace)
        assertEquals("Welcome to burrito town!", resultShouldNotReplace)
    }

    @Test
    fun `default filter removes word`() {
        val resultShouldRemove = Kofanity.remove(badwordsTestString)
        val resultShouldNotRemove = Kofanity.remove(badwords2TestString)

        assertEquals("Welcome to town!", resultShouldRemove)
        assertEquals("Welcome to burrito town!", resultShouldNotRemove)
    }

    @Test
    fun `new filter checks if word needs to be filtered`() {
        setUpNewFilter()
        val resultShouldNeedFiltering = Kofanity.needsFiltering(badwords2TestString)
        val resultShouldNotNeedFiltering = Kofanity.needsFiltering(badwordsTestString)

        assertEquals(true, resultShouldNeedFiltering)
        assertEquals(false, resultShouldNotNeedFiltering)
    }

    @Test
    fun `new filter replaces word`() {
        setUpNewFilter()
        val resultShouldReplace = Kofanity.replace(badwords2TestString)
        val resultShouldNotReplace = Kofanity.replace(badwordsTestString)

        assertEquals("Welcome to xxxxxxx town!", resultShouldReplace)
        assertEquals("Welcome to taco town!", resultShouldNotReplace)
    }

    @Test
    fun `new filter removes word`() {
        setUpNewFilter()
        val resultShouldRemove = Kofanity.remove(badwords2TestString)
        val resultShouldNotRemove = Kofanity.remove(badwordsTestString)

        assertEquals("Welcome to <REDACTED> town!", resultShouldRemove)
        assertEquals("Welcome to taco town!", resultShouldNotRemove)
    }

    private fun setUpNewFilter() {
        Kofanity.setFilter(
            PaperFilter(
                Rule(
                    path = "/badwords2.txt",
                    replacementChar = 'x',
                    removalString = "<REDACTED>"
                )
            )
        )
    }
}