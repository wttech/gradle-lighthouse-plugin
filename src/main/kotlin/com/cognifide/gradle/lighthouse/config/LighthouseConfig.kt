package com.cognifide.gradle.lighthouse.config

import com.cognifide.gradle.lighthouse.LighthouseExtension
import com.cognifide.gradle.lighthouse.Utils
import org.gradle.api.Project
import java.io.File

class LighthouseConfig {

    var suites: List<LighthouseSuite> = listOf()

    companion object {

        fun from(file: File) = file.run {
            Utils.fromJson(readText(), LighthouseConfig::class.java)
        }

        fun default(project: Project) = from(LighthouseExtension.get(project).suiteConfigFile)
    }
}

