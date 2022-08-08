package org.slenium.tests

import io.qameta.allure.Feature
import org.slenium.pom.api.actions.CartApi
import org.slenium.pom.api.actions.SignUpApi
import org.slenium.pom.base.BaseTest
import org.slenium.pom.objects.BillingAddress
import org.slenium.pom.objects.Product
import org.slenium.pom.objects.User
import org.slenium.pom.pages.CheckoutPage
import org.slenium.pom.utils.FakerUtils
import org.slenium.pom.utils.JacksonUtils
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

@Feature("Checkout")
class CheckoutTest : BaseTest() {

    @Test
    fun guestCheckoutUsingDirectBankTransfer() {
        val billingAddress = JacksonUtils().deserializeJson(
            fileName = "myBillingAddress.json",
            classOfT = BillingAddress::class.java
        )
        val checkoutPage = CheckoutPage(getDriver()).load()
        val cartApi = CartApi()
        cartApi.addToCart(1215, 1)
        injectCookiesToBrowser(cartApi.getCookies())

        checkoutPage.load()
            .setBillingAddress(billingAddress)
            .selectDirectBankTransfer()
            .placeOrder()
        assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.")
    }

    @Test
    fun loginAndCheckoutUsingDirectBankTransfer() {
        val billingAddress = JacksonUtils().deserializeJson(
            fileName = "myBillingAddress.json",
            classOfT = BillingAddress::class.java
        )
        val username = "demouser${FakerUtils().generateRandomNumber()}"
        val user = User(
            username = username,
            password = "demouserpwd",
            email = "$username@gmail.com"
        )
        val signUpApi = SignUpApi()
        signUpApi.register(user)
        val cartApi = CartApi(signUpApi.getCookies())
        val product = Product().findProduct(id = 1215)
        cartApi.addToCart(product.id!!, 1)

        val checkoutPage = CheckoutPage(getDriver()).load()
        injectCookiesToBrowser(signUpApi.getCookies())

        checkoutPage.load()
            .setBillingAddress(billingAddress)
            .selectDirectBankTransfer()
            .placeOrder()
        assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.")
    }
}
