package com.cognifide.gradle.lighthouse

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.FilenameUtils

object Utils {

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return ObjectMapper().readValue(json, clazz)
    }

    fun wildcardMatch(value: String?, matcher: String?): Boolean {
        if (matcher.isNullOrBlank()) {
            return false
        }

        return matcher.split(",").any { FilenameUtils.wildcardMatch(value.orEmpty(), it) }
    }
}
