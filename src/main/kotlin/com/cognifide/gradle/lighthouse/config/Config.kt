package com.cognifide.gradle.lighthouse.config

import com.cognifide.gradle.lighthouse.LighthouseException
import com.cognifide.gradle.lighthouse.Utils
import java.io.File

class Config {

    var suites: List<Suite> = listOf()

    companion object {

        fun from(file: File) = file.run {
            if (!exists()) {
                throw LighthouseException("Lighthouse configuration file does not exists: $file!")
            }

            Utils.fromJson(readText(), Config::class.java)
        }
    }
}
