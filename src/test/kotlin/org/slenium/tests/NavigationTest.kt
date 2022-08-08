package org.slenium.tests

import io.qameta.allure.Feature
import org.slenium.pom.base.BaseTest
import org.slenium.pom.pages.HomePage
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

@Feature("Navigation")
class NavigationTest : BaseTest() {

    @Test
    fun navigateFromHomeToStoreUsingMainMenu() {
        val storePage = HomePage(getDriver())
            .load()
            .getMyHeader()
            .navigateToStoreUsingMenu()
        assertEquals(storePage.getTitle(), "Store")
    }
}
