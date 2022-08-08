package org.slenium.pom.api.actions

import io.restassured.RestAssured.given
import io.restassured.http.Cookies
import io.restassured.http.Header
import io.restassured.http.Headers
import io.restassured.response.Response
import org.jsoup.Jsoup
import org.slenium.pom.objects.User
import org.slenium.pom.utils.ConfigLoader
import java.lang.RuntimeException

class SignUpApi {

    private var cookies = Cookies()

    fun getCookies(): Cookies {
        return this.cookies
    }

    fun fetchRegisterNonceValueUsingGroovy(): String {
        val response = getAccount()
        return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-register-nonce' }.@value")
    }

    fun fetchRegisterNonceValueUsingJsoup(): String {
        val response = getAccount()
        val doc = Jsoup.parse(response.body().prettyPrint())
        val element = doc.selectFirst("#woocommerce-register-nonce")
        return element!!.attr("value")
    }

    fun getAccount(): Response {
        val response = given()
            .baseUri(ConfigLoader.getBaseUrl())
            .cookies(Cookies())
            .log()
            .all()
            .`when`()
            .get("/account")
            .then()
            .log()
            .all()
            .extract()
            .response()
        if (response.statusCode != 200) {
            throw RuntimeException("Failed to fetch the account, HTTP Status Code: ${response.statusCode}")
        }
        return response
    }

    fun register(user: User): Response {
        val header = Header("content-type", "application/x-www-form-urlencoded")
        val headers = Headers(header)
        val formParams = hashMapOf(
            "username" to user.username,
            "email" to user.email,
            "password" to user.password,
            "woocommerce-register-nonce" to fetchRegisterNonceValueUsingJsoup(),
            "register" to "Register"
        )
        val response = given()
            .baseUri(ConfigLoader.getBaseUrl())
            .headers(headers)
            .formParams(formParams)
            .cookies(Cookies())
            .log()
            .all()
            .`when`()
            .post("/account")
            .then()
            .log()
            .all()
            .extract()
            .response()
        if (response.statusCode != 302) {
            throw RuntimeException("Failed to register the account, HTTP Status Code: ${response.statusCode}")
        }
        this.cookies = response.detailedCookies
        return response
    }
}
