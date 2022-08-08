package org.slenium.pom.pages

import org.openqa.selenium.WebDriver
import org.slenium.pom.base.BasePage
import org.slenium.pom.pages.components.MyHeader
import org.slenium.pom.pages.components.ProductThumbnail

class HomePage(classDriver: WebDriver) : BasePage(driver = classDriver) {
    private val myHeader: MyHeader = MyHeader(baseDriver)
    private val productThumbnail = ProductThumbnail(baseDriver)

    fun load(): HomePage {
        load("/")
        return this
    }

    fun getMyHeader(): MyHeader {
        return this.myHeader
    }

    fun getProductThumbnail(): ProductThumbnail {
        return this.productThumbnail
    }
}
