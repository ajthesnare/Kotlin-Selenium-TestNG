package org.slenium.pom.factory

class DriverManagerFactory {

    fun getManager(browser: String): DriverManager {
        return when (browser.lowercase()) {
            "chrome" -> ChromeDriverManager()
            "firefox" -> FirefoxDriverManager()
            else -> throw IllegalStateException("Invalid browser name: $browser")
        }
    }
}
