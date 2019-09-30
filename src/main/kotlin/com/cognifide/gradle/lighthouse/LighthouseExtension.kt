package com.cognifide.gradle.lighthouse

import com.cognifide.gradle.lighthouse.config.LighthouseConfig
import com.cognifide.gradle.lighthouse.runner.LighthouseRunner
import com.cognifide.gradle.lighthouse.runner.LighthouseUnit
import org.gradle.api.Project
import java.io.File

class LighthouseExtension(val project: Project) {

    var configFile = project.file(project.findProperty("lighthouse.configFile") as String? ?: "lighthouse/suites.json")

    val config get() = LighthouseConfig.from(configFile)

    var suiteName = project.findProperty("lighthouse.suite") as String?

    var baseUrl = project.findProperty("lighthouse.baseUrl") as String?

    var reportFileRule: (LighthouseUnit) -> File = {
        project.file("build/lighthouse/${it.suite.name}/${it.url.substringAfter("://").replace("/", "_")}")
    }

    fun <T> runner(consumer: LighthouseRunner.() -> T) = LighthouseRunner(this).run(consumer)

    companion object {
        fun get(project: Project) = project.extensions.getByType(LighthouseExtension::class.java)
    }
}
