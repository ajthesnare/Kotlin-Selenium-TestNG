package org.slenium.pom.pages.components

import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.slenium.pom.base.BasePage
import org.slenium.pom.pages.CartPage

class ProductThumbnail(classDriver: WebDriver) : BasePage(driver = classDriver) {

    private val viewCartLink = By.cssSelector("a[title='View cart']")

    private fun getAddToCartButtonElement(productName: String): By {
        return By.cssSelector("a[aria-label='Add “$productName” to your cart']")
    }

    @Step
    fun clickAddToCartButton(productName: String): ProductThumbnail {
        val addToCartButton = getAddToCartButtonElement(productName = productName)
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click()
        return this
    }

    @Step
    fun clickViewCart(): CartPage {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click()
        return CartPage(baseDriver)
    }
}
