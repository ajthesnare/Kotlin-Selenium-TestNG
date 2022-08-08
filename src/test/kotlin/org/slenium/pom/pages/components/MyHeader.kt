package org.slenium.pom.pages.components

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.slenium.pom.base.BasePage
import org.slenium.pom.pages.StorePage

class MyHeader(classDriver: WebDriver) : BasePage(driver = classDriver) {
    private val storeMenuLink = By.cssSelector("#menu-item-1227 > a")

    /**
     * Returning a new page is known as the "Fluent Pattern"
     * and is meant to reduce page initializations in a test.
     */
    fun navigateToStoreUsingMenu(): StorePage {
        wait.until(ExpectedConditions.elementToBeClickable(storeMenuLink)).click()
        return StorePage(baseDriver)
    }
}
