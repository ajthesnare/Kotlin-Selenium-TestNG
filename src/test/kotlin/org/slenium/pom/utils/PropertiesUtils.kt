package org.slenium.pom.utils

import java.io.BufferedReader
import java.io.FileReader
import java.util.Properties

class PropertiesUtils {
    fun propertyLoader(filePath: String): Properties {
        val reader = BufferedReader(FileReader(filePath))
        val properties = Properties()
        properties.load(reader)
        reader.close()
        return properties
    }
}
