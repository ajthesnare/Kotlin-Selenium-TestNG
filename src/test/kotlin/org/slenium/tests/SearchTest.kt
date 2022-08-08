package org.slenium.tests

import io.qameta.allure.Feature
import org.slenium.pom.base.BaseTest
import org.slenium.pom.pages.StorePage
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

@Feature("Search")
class SearchTest : BaseTest() {

    @Test
    fun searchWithPartialMatch() {
        val searchFor = "Blue"
        val storePage = StorePage(getDriver())
            .load()
            .search(searchFor)
        assertEquals(storePage.getTitle(), "Search results: “$searchFor”")
    }
}
