package com.cognifide.gradle.lighthouse

import com.cognifide.gradle.lighthouse.config.Config
import com.cognifide.gradle.lighthouse.runner.Runner
import com.cognifide.gradle.lighthouse.runner.RunUnit
import org.gradle.api.Project
import java.io.File
import com.cognifide.gradle.lighthouse.Utils.prop

class LighthouseExtension(val project: Project) {

    val configFile = project.objects.fileProperty().apply {
        set(project.layout.projectDirectory.file("lighthouse/suites.json"))
        project.prop("lighthouse.configFile")?.let { set(project.file(it)) }
    }

    val config = project.objects.property(Config::class.java).apply {
        convention(configFile.map { Config.from(it.asFile) })
    }

    val suiteName = project.objects.property(String::class.java).apply {
        project.prop("lighthouse.suite")?.let { set(it) }
    }

    val baseUrl = project.objects.property(String::class.java).apply {
        project.prop("lighthouse.baseUrl")?.let { set(it) }
    }

    var reportFileRule: (RunUnit) -> File = {
        val fileName = it.url.substringAfter("://").replace("/", "_").removeSuffix("_")
        project.file("build/lighthouse/${it.suite.name}/$fileName.html")
    }

    fun <T> runner(consumer: Runner.() -> T) = Runner(this).run(consumer)

    companion object {
        fun get(project: Project) = project.extensions.getByType(LighthouseExtension::class.java)
    }
}
