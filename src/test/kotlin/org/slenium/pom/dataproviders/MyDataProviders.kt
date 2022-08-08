package org.slenium.pom.dataproviders

import org.slenium.pom.objects.Product
import org.slenium.pom.utils.JacksonUtils
import org.testng.annotations.DataProvider

class MyDataProviders {

    @DataProvider(name = "getFeaturedProducts", parallel = true)
    fun getFeaturedProducts(): Array<Product> {
        return JacksonUtils().deserializeJson("products.json", Array<Product>::class.java)
    }
}
