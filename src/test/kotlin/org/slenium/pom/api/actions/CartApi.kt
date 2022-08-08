package org.slenium.pom.api.actions

import io.restassured.RestAssured
import io.restassured.http.Cookies
import io.restassured.http.Cookies.cookies
import io.restassured.http.Header
import io.restassured.http.Headers
import io.restassured.response.Response
import org.slenium.pom.utils.ConfigLoader
import java.lang.RuntimeException

class CartApi(
    private var cookies: Cookies = Cookies()
) {

    fun getCookies(): Cookies {
        return this.cookies
    }

    fun addToCart(productId: Int, quantity: Int): Response {
        val header = Header("content-type", "application/x-www-form-urlencoded")
        val headers = Headers(header)
        val formParams = hashMapOf(
            "product_sku" to "",
            "product_id" to productId,
            "quantity" to quantity
        )
        val response = RestAssured.given()
            .baseUri(ConfigLoader.getBaseUrl())
            .headers(headers)
            .formParams(formParams)
            .cookies(getCookies())
            .log()
            .all()
            .`when`()
            .post("/?wc-ajax=add_to_cart")
            .then()
            .log()
            .all()
            .extract()
            .response()
        if (response.statusCode != 200) {
            throw RuntimeException("Failed to add product $productId to the cart, HTTP Status Code: ${response.statusCode}")
        }
        this.cookies = response.detailedCookies
        return response
    }
}
