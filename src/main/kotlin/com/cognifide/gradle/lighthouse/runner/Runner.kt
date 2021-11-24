package com.cognifide.gradle.lighthouse.runner

import com.cognifide.gradle.lighthouse.LighthouseException
import com.cognifide.gradle.lighthouse.LighthouseExtension
import com.cognifide.gradle.lighthouse.Utils
import com.github.gradle.node.yarn.exec.YarnExecRunner
import java.io.File

class Runner(lighthouse: LighthouseExtension) {

    private var project = lighthouse.project

    private var logger = project.logger

    var config = lighthouse.config.get()

    var suiteName = lighthouse.suiteName.orNull

    var baseUrl = lighthouse.baseUrl.orNull

    var reportFileRule = lighthouse.reportFileRule

    fun runSuites() {
        when {
            !suiteName.isNullOrBlank() -> config.suites.filter { Utils.wildcardMatch(it.name, suiteName) }
            else -> config.suites.filter { it.baseUrl?.run { this == baseUrl } ?: false }
        }.ifEmpty {
            config.suites.filter { it.default }
        }.ifEmpty {
            throw LighthouseException("Cannot determine any Lighthouse test suites to run!\n" +
                    "Consider setting default flags for some suites to handle any base URL " +
                    "or when skipping suite name parameter.")
        }.forEach { suite ->
            logger.info("Running Lighthouse Suite '${suite.name}'")
            suite.paths.forEach { path ->
                val baseUrlOverride = if (baseUrl.isNullOrBlank()) suite.baseUrl else baseUrl
                val url = "$baseUrlOverride$path"
                val unit = RunUnit(suite, url)
                val reportFile = reportFileRule(unit)

                run(url, reportFile, suite.args)
            }
        }
    }

    fun run(url: String, reportFile: File, extraArgs: List<String> = listOf()) {
        logger.info("Running Lighthouse Test '$url'")

        val reportDir = reportFile.parentFile
        val reportName = reportFile.name

        reportDir.mkdirs()

        // TODO needs https://github.com/node-gradle/gradle-node-plugin/pull/202
        project.objects.newInstance(YarnExecRunner::class.java).apply {
            workingDir = project.projectDir
            arguments = mutableListOf("lighthouse-ci", url, "--report=$reportDir", "--filename=$reportName") + extraArgs
            execute()
        }
    }
}
