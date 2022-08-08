package org.slenium.pom.base

import io.restassured.http.Cookies
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.slenium.pom.factory.DriverManagerFactory
import org.slenium.pom.utils.CookiesUtils
import org.testng.ITestResult
import org.testng.ITestResult.FAILURE
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import java.io.File
import javax.imageio.ImageIO

open class BaseTest {

    private val browser = System.getProperty("browser", "chrome")
    private var driver: ThreadLocal<WebDriver> = ThreadLocal<WebDriver>()

    private fun setDriver(driver: WebDriver) {
        this.driver.set(driver)
    }

    protected fun getDriver(): WebDriver {
        return this.driver.get()
    }

    /**
     * @Synchronized means only one thread will execute at a time.
     * For starting and stopping the driver, this may help reduce selenium issues on shared VMs.
     */
    @BeforeMethod
    @Synchronized
    fun startDriver() {
        setDriver(DriverManagerFactory().getManager(browser).createDriver())
        println("@BEFORE_METHOD, CURRENT THREAD: ${Thread.currentThread().id}, DRIVER = ${getDriver()}")
    }

    @AfterMethod
    @Synchronized
    fun quitDriver(result: ITestResult) {
        Thread.sleep(300) // Try to avoid Selenium's "Connection reset" error.
        println("@AFTER_METHOD, CURRENT THREAD: ${Thread.currentThread().id}, DRIVER = ${getDriver()}")
        try {
            if (result.status == FAILURE) {
                val destFile = File("scr/$browser/${result.testClass.realClass.simpleName}_${result.method.methodName}.png")
//                takeScreenshot(destFile)
                takeScreenshotWithAShot(destFile)
            }
        } catch (t: Throwable) {
            println("FAILED TO CAPTURE SCREENSHOT FOR FAILED TEST")
        } finally {
            getDriver().quit()
        }
    }

    fun injectCookiesToBrowser(cookies: Cookies) {
        val seleniumCookies = CookiesUtils().convertRestAssuredCookiesToSeleniumCookies(cookies)
        seleniumCookies.forEach {
            getDriver().manage().addCookie(it)
        }
    }

    /**
     * Take a screenshot of the viewport.
     * This method creates the destination if it does not exist.
     */
    private fun takeScreenshot(destFile: File) {
        println("SCREENSHOT LOCATION: $destFile")
        val scrFile = (getDriver() as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        FileUtils.copyFile(scrFile, destFile)
    }

    /**
     * Take a screenshot of the entire page.
     * This method does NOT create the destination if it does not exist.
     */
    private fun takeScreenshotWithAShot(destFile: File) {
        val screenshot = AShot()
            .shootingStrategy(ShootingStrategies.viewportPasting(100))
            .takeScreenshot(getDriver())
        ImageIO.write(screenshot.image, "PNG", destFile)
    }
}
