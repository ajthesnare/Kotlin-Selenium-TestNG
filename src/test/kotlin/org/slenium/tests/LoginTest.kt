package org.slenium.tests

import io.qameta.allure.Feature
import org.slenium.pom.api.actions.CartApi
import org.slenium.pom.api.actions.SignUpApi
import org.slenium.pom.base.BaseTest
import org.slenium.pom.objects.Product
import org.slenium.pom.objects.User
import org.slenium.pom.pages.CheckoutPage
import org.slenium.pom.utils.FakerUtils
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import java.sql.DriverManager.getDriver

@Feature("Login")
class LoginTest : BaseTest() {

    @Test
    fun loginDuringCheckout() {
        val username = "demouser${FakerUtils().generateRandomNumber()}"
        val user = User(
            username = username,
            password = "demouserpwd",
            email = "$username@gmail.com"
        )
        val signUpApi = SignUpApi()
        signUpApi.register(user)
        val product = Product().findProduct(id = 1215)
        val cartApi = CartApi()
        cartApi.addToCart(product.id!!, 1)

        val checkoutPage = CheckoutPage(getDriver()).load()
        injectCookiesToBrowser(cartApi.getCookies())

        checkoutPage.load()
            .clickHereToLoginLink()
            .login(user)
        assertTrue(checkoutPage.getProductName().contains(product.name!!))
    }
}
