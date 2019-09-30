package com.cognifide.gradle.lighthouse.config

import com.cognifide.gradle.lighthouse.LighthouseException
import com.cognifide.gradle.lighthouse.Utils
import java.io.File

class LighthouseConfig {

    var suites: List<LighthouseSuite> = listOf()

    companion object {

        fun from(file: File) = file.run {
            if (!exists()) {
                throw LighthouseException("Lighthouse configuration file does not exists: $file!")
            }

            Utils.fromJson(readText(), LighthouseConfig::class.java)
        }
    }
}

