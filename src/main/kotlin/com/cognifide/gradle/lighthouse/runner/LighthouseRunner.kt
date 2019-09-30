package com.cognifide.gradle.lighthouse.runner

import com.cognifide.gradle.lighthouse.LighthouseExtension
import com.cognifide.gradle.lighthouse.Utils
import com.cognifide.gradle.lighthouse.config.LighthouseConfig
import com.moowork.gradle.node.yarn.YarnExecRunner
import java.io.File

class LighthouseRunner(lighthouse: LighthouseExtension) {

    private var project = lighthouse.project

    private var logger = project.logger

    var config: LighthouseConfig = lighthouse.config

    var reportFileRule = lighthouse.reportFileRule

    var suiteName = lighthouse.suiteName

    var baseUrl = lighthouse.baseUrl

    fun runSuites() {
        when {
            !suiteName.isNullOrBlank() -> config.suites.filter { Utils.wildcardMatch(it.name, suiteName) }
            else -> config.suites.filter { it.baseUrl?.run { this == baseUrl } ?: false }
        }.ifEmpty {
            config.suites.filter { it.name == "default" }
        }.forEach { suite ->
            logger.info("Running Lighthouse Suite '${suite.name}'")

            suite.paths.forEach { path ->
                val url = "$baseUrl$path"
                val unit = LighthouseUnit(suite, url)
                val reportFile = reportFileRule(unit)

                run(url, reportFile)
            }
        }
    }

    fun run(url: String, reportFile: File, extraArgs: List<String> = listOf()) {
        val reportDir = reportFile.parentFile
        val reportName = reportFile.name

        reportDir.mkdirs()
        YarnExecRunner(project).apply {
            workingDir = project.projectDir
            arguments = mutableListOf("lighthouse-ci", url, "--report=$reportDir", "--filename=$reportName") + extraArgs
            execute()
        }
    }
}
