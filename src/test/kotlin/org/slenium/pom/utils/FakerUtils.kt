package org.slenium.pom.utils

import com.github.javafaker.Faker

class FakerUtils {

    fun generateRandomNumber(): Long {
        return Faker().number().randomNumber(10, true)
    }
}
