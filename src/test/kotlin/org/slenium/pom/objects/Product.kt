package org.slenium.pom.objects

import io.qameta.allure.Step
import org.openqa.selenium.NotFoundException
import org.slenium.pom.utils.JacksonUtils

data class Product(
    val id: Int? = null,
    val featured: Boolean? = null,
    val name: String? = null
) {
    @Step
    fun findProduct(id: Int): Product {
        val products = JacksonUtils().deserializeJson("products.json", Array<Product>::class.java)
        products.forEach {
            if (it.id == id) {
                return Product(id = it.id, featured = it.featured, name = it.name)
            }
        }
        throw NotFoundException()
    }
}
