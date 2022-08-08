package org.slenium.pom.utils

import io.restassured.http.Cookies
import org.openqa.selenium.Cookie

class CookiesUtils {

    fun convertRestAssuredCookiesToSeleniumCookies(cookies: Cookies): List<Cookie> {
        val restAssuredCookies = cookies.asList()
        val seleniumCookies = arrayListOf<Cookie>()
        for (restAssuredCookie in restAssuredCookies) {
            seleniumCookies.add(
                Cookie(
                    restAssuredCookie.name,
                    restAssuredCookie.value,
                    restAssuredCookie.domain,
                    restAssuredCookie.path,
                    restAssuredCookie.expiryDate,
                    restAssuredCookie.isSecured,
                    restAssuredCookie.isHttpOnly,
                    restAssuredCookie.sameSite
                )
            )
        }
        return seleniumCookies
    }
}
