package org.slenium.pom.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.CacheLookup
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.slenium.pom.base.BasePage

class CartPage(classDriver: WebDriver) : BasePage(driver = classDriver) {

    @FindBy(css = "td[class='product-name'] a")
    private lateinit var productName: WebElement

    @FindBy(css = ".checkout-button")
    @CacheLookup
    private lateinit var checkoutButton: WebElement

    init {
        PageFactory.initElements(baseDriver, this)
    }

    fun getProductName(): String {
        return wait.until(ExpectedConditions.visibilityOf(productName)).text
    }

    fun checkout(): CheckoutPage {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click()
        return CheckoutPage(baseDriver)
    }
}
