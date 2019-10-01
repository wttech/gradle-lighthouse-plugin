package com.cognifide.gradle.lighthouse.config

class Suite {

    lateinit var name: String

    var baseUrl: String? = null

    var paths: List<String> = listOf()

    var args: List<String> = listOf()
}
