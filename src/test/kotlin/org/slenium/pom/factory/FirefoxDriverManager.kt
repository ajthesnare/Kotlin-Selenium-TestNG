package org.slenium.pom.factory

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class FirefoxDriverManager : DriverManager() {

    override fun createDriver(): WebDriver {
        WebDriverManager.firefoxdriver()
            .cachePath(driverCachePath)
            .setup()
        val options = FirefoxOptions()
        return if (runOnGrid) {
            RemoteWebDriver(URL(gridUrl), options)
        } else {
            FirefoxDriver(options)
        }
    }
}
