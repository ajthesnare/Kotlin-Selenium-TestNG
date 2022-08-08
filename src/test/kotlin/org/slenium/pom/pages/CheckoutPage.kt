package org.slenium.pom.pages

import org.bouncycastle.asn1.x509.X509ObjectIdentifiers.countryName
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.slenium.pom.base.BasePage
import org.slenium.pom.objects.BillingAddress
import org.slenium.pom.objects.User

class CheckoutPage(classDriver: WebDriver) : BasePage(driver = classDriver) {
    private val firstNameField = By.id("billing_first_name")
    private val lastNameField = By.id("billing_last_name")
    private val addressLineOneField = By.id("billing_address_1")
    private val billingCityField = By.id("billing_city")
    private val billingPostCodeField = By.id("billing_postcode")
    private val billingEmailField = By.id("billing_email")
    private val placeOrderButton = By.id("place_order")
    private val successNotice = By.cssSelector(".woocommerce-notice")

    private val clickHereToLoginLink = By.className("showlogin")
    private val usernameField = By.id("username")
    private val passwordField = By.id("password")
    private val loginButton = By.name("login")
    private val overlay = By.cssSelector(".blockUI.blockOverlay")

    // private val countryDropdown = By.id("billing_country")
    private val alternativeCountryDropdown = By.id("select2-billing_country-container")

    // private val stateDropdown = By.id("billing_state")
    private val alternativeStateDropdown = By.id("select2-billing_state-container")

    private val directBankTransferRadioButton = By.id("payment_method_bacs")

    private val productName = By.cssSelector("td[class='product-name']")

    fun load(): CheckoutPage {
        load("/checkout/")
        return this
    }

    fun enterFirstName(firstName: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField))
        e.clear()
        e.sendKeys(firstName)
        return this
    }

    fun enterLastName(lastName: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField))
        e.clear()
        e.sendKeys(lastName)
        return this
    }

    fun selectCountry(countryName: String): CheckoutPage {
        /**
         * The follow code works in Chrome but not Firefox.
         *
         * val select = Select(wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)))
         * select.selectByVisibleText(countryName)
         */

        /**
         * The following code works in both Chrome and Firefox.
         */
        wait.until(ExpectedConditions.elementToBeClickable(alternativeCountryDropdown)).click()
        val e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='$countryName']")))
        (baseDriver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", e)
        e.click()
        return this
    }

    fun enterAddressLineOneField(addressLineOne: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOneField))
        e.clear()
        e.sendKeys(addressLineOne)
        return this
    }

    fun enterCity(city: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingCityField))
        e.clear()
        e.sendKeys(city)
        return this
    }

    fun selectState(stateName: String): CheckoutPage {
        /**
         * The follow code works in Chrome but not Firefox.
         *
         * val select = Select(wait.until(ExpectedConditions.elementToBeClickable(stateDropdown)))
         * select.selectByVisibleText(stateName)
         */

        /**
         * The following code works in both Chrome and Firefox.
         */
        wait.until(ExpectedConditions.elementToBeClickable(alternativeStateDropdown)).click()
        val e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='$stateName']")))
        (baseDriver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", e)
        e.click()
        return this
    }

    fun enterPostCode(postCode: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingPostCodeField))
        e.clear()
        e.sendKeys(postCode)
        return this
    }

    fun enterEmail(email: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingEmailField))
        e.clear()
        e.sendKeys(email)
        return this
    }

    fun setBillingAddress(billingAddress: BillingAddress): CheckoutPage {
        enterFirstName(billingAddress.firstName)
            .enterLastName(billingAddress.lastName)
            .selectCountry(billingAddress.country)
            .enterAddressLineOneField(billingAddress.addressLineOne)
            .enterCity(billingAddress.city)
            .selectState(billingAddress.state)
            .enterPostCode(billingAddress.postalCode)
            .enterEmail(billingAddress.email)
        return this
    }

    fun placeOrder(): CheckoutPage {
        waitForOverlaysToDisappear(overlay)
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click()
        return this
    }

    fun getNotice(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).text
    }

    fun clickHereToLoginLink(): CheckoutPage {
        wait.until(ExpectedConditions.elementToBeClickable(clickHereToLoginLink)).click()
        return this
    }

    private fun enterUsername(username: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField))
        e.clear()
        e.sendKeys(username)
        return this
    }

    private fun enterPassword(password: String): CheckoutPage {
        val e = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField))
        e.clear()
        e.sendKeys(password)
        return this
    }

    private fun clickLoginButton(): CheckoutPage {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click()
        return this
    }

    private fun waitForLoginButtonToDisappear(): CheckoutPage {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginButton))
        return this
    }

    fun login(user: User): CheckoutPage {
        return enterUsername(user.username)
            .enterPassword(user.password)
            .clickLoginButton()
            .waitForLoginButtonToDisappear()
    }

    fun selectDirectBankTransfer(): CheckoutPage {
        val e = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioButton))
        if (!e.isSelected) {
            e.click()
        }
        return this
    }

    fun getProductName(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).text
    }
}
