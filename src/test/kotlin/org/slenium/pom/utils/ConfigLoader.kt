package org.slenium.pom.utils

import org.slenium.pom.constants.EnvType
import java.util.Properties

object ConfigLoader {
    private var properties: Properties
    private val env = System.getProperty("env", EnvType.STAGE.toString())

    init {
        properties = when (EnvType.valueOf(env).toString()) {
            "PRODUCTION" -> PropertiesUtils().propertyLoader("src/test/resources/prod.config.properties")
            "STAGE" -> PropertiesUtils().propertyLoader("src/test/resources/stg.config.properties")
            else -> throw IllegalStateException("Invalid env type: $env")
        }
    }

    fun getBaseUrl(): String {
        val prop = properties.getProperty("baseUrl")
        if (prop != null) {
            return prop
        } else {
            throw java.lang.RuntimeException("Property baseUrl is not specified in the config properties file")
        }
    }

    fun getUsername(): String {
        val prop = properties.getProperty("username")
        if (prop != null) {
            return prop
        } else {
            throw java.lang.RuntimeException("Property baseUrl is not specified in the config properties file")
        }
    }

    fun getPassword(): String {
        val prop = properties.getProperty("password")
        if (prop != null) {
            return prop
        } else {
            throw java.lang.RuntimeException("Property baseUrl is not specified in the config properties file")
        }
    }
}
