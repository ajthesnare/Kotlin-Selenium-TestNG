package org.slenium.pom.factory

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class ChromeDriverManager : DriverManager() {

    override fun createDriver(): WebDriver {
        WebDriverManager.chromedriver()
            .cachePath(driverCachePath)
            .setup()
        val options = ChromeOptions()
        return if (runOnGrid) {
            RemoteWebDriver(URL(gridUrl), options)
        } else {
            ChromeDriver(options)
        }
    }
}
