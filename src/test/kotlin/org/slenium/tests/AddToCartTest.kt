package org.slenium.tests

import io.qameta.allure.Description
import io.qameta.allure.Feature
import io.qameta.allure.Issue
import org.slenium.pom.base.BaseTest
import org.slenium.pom.dataproviders.MyDataProviders
import org.slenium.pom.objects.Product
import org.slenium.pom.pages.HomePage
import org.slenium.pom.pages.StorePage
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

// @Epic("EPIC")
@Feature("Add to cart")
class AddToCartTest : BaseTest() {

    // @Story("Story")
    @Issue("BUG-26089")
    @Description("This test should fail to demonstrate screenshots.")
    @Test(description = "Add to cart from store page")
    fun addToCartFromStorePage() {
        val product = Product().findProduct(id = 1215)
        val cartPage = StorePage(getDriver())
            .load()
            .getProductThumbnail()
            .clickAddToCartButton(product.name!!)
            .clickViewCart()
        assertEquals(cartPage.getProductName(), "product.name")
    }

    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProviders::class)
    fun addToCartFeaturedProducts(product: Product) {
        val productName = product.name!!
        val cartPage = HomePage(getDriver())
            .load()
            .getProductThumbnail()
            .clickAddToCartButton(productName)
            .clickViewCart()
        assertEquals(cartPage.getProductName(), productName)
    }
}
