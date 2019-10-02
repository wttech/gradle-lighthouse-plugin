package com.cognifide.gradle.lighthouse

import com.cognifide.gradle.lighthouse.config.Config
import com.cognifide.gradle.lighthouse.runner.Runner
import com.cognifide.gradle.lighthouse.runner.RunUnit
import org.gradle.api.Project
import java.io.File
import com.cognifide.gradle.lighthouse.Utils.prop

class LighthouseExtension(val project: Project) {

    var configFile = project.file(project.prop("lighthouse.configFile") ?: "lighthouse/suites.json")

    val config get() = Config.from(configFile)

    var suiteName = project.prop("lighthouse.suite")

    var baseUrl = project.prop("lighthouse.baseUrl")

    var reportFileRule: (RunUnit) -> File = {
        project.file("build/lighthouse/${it.suite.name}/${it.url.substringAfter("://").replace("/", "_")}")
    }

    fun <T> runner(consumer: Runner.() -> T) = Runner(this).run(consumer)

    companion object {
        fun get(project: Project) = project.extensions.getByType(LighthouseExtension::class.java)
    }
}
