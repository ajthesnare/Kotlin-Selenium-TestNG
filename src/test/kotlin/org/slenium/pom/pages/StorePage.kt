package org.slenium.pom.pages

import io.qameta.allure.Step
import org.bouncycastle.asn1.x500.style.RFC4519Style.title
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.slenium.pom.base.BasePage
import org.slenium.pom.pages.components.ProductThumbnail
import org.slenium.pom.utils.ConfigLoader
import java.awt.SystemColor.text

class StorePage(classDriver: WebDriver) : BasePage(driver = classDriver) {
    private val searchField = By.id("woocommerce-product-search-field-0")
    private val searchButton = By.cssSelector("button[value='Search']")
    private val title = By.cssSelector(".woocommerce-products-header__title.page-title")
    private val productThumbnail = ProductThumbnail(baseDriver)

    @Step
    fun load(): StorePage {
        load("/store")
        return this
    }

    @Step
    fun getProductThumbnail(): ProductThumbnail {
        return this.productThumbnail
    }

    /**
     * Returning the page object is known as the "Builder Pattern"
     * and is meant to chain page actions.
     */
    fun search(text: String): StorePage {
        enterTextIntoSearchField(text = text).clickSearchButton()
        wait.until(ExpectedConditions.urlToBe("${ConfigLoader.getBaseUrl()}/?s=$text&post_type=product"))
        return this
    }

    private fun enterTextIntoSearchField(text: String): StorePage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField))
        e.sendKeys(text)
        return this
    }

    private fun clickSearchButton(): StorePage {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click()
        return this
    }

    fun getTitle(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).text
    }
}
