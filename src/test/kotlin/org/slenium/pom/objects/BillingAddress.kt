package org.slenium.pom.objects

data class BillingAddress(
    val firstName: String = "",
    val lastName: String = "",
    val addressLineOne: String = "",
    val city: String = "",
    val postalCode: String = "",
    val email: String = "",
    val country: String = "",
    val state: String = ""
)
