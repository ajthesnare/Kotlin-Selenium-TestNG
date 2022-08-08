package org.slenium.pom.factory

import org.openqa.selenium.WebDriver

abstract class DriverManager {

    protected val driverCachePath = "drivers"
    protected val runOnGrid = System.getProperty("runOnGrid", "false").toBoolean()
    protected val gridUrl = "https://selenium-grid.ops-prod.internal.virginpulse.com"

    abstract fun createDriver(): WebDriver
}
