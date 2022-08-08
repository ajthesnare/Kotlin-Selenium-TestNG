package org.slenium.pom.base

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slenium.pom.utils.ConfigLoader
import java.time.Duration

open class BasePage(driver: WebDriver) {
    protected var baseDriver: WebDriver
    protected var wait: WebDriverWait

    init {
        this.baseDriver = driver
        this.wait = WebDriverWait(baseDriver, Duration.ofSeconds(30))
    }

    fun load(endPoint: String) {
        baseDriver.get("${ConfigLoader.getBaseUrl()}$endPoint")
    }

    fun waitForOverlaysToDisappear(overlay: By) {
        val overlays = baseDriver.findElements(overlay)
        if (overlays.size > 0) {
            wait.until(ExpectedConditions.invisibilityOfAllElements(overlays))
        }
    }
}
