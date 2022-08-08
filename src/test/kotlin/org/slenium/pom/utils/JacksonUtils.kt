package org.slenium.pom.utils

import com.fasterxml.jackson.databind.ObjectMapper

class JacksonUtils {

    fun <T> deserializeJson(fileName: String, classOfT: Class<T>): T {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)!!
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(inputStream, classOfT)
    }
}
