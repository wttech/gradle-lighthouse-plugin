package com.cognifide.gradle.lighthouse.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Suite {

    lateinit var name: String

    var default: Boolean = false

    var baseUrl: String? = null

    var paths: List<String> = listOf()

    var args: List<String> = listOf()
}
