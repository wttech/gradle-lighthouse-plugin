package com.cognifide.gradle.lighthouse

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class LighthousePluginFunctionalTest {

    @Test
    fun `can run suites`() {
        // given
        val projectDir = File("build/functionalTest")

        with(projectDir) {
            mkdirs()
            resolve("settings.gradle.kts").writeText("")
            resolve("build.gradle.kts").writeText("""
            plugins {
                id("com.cognifide.lighthouse")
            }
            """.trimIndent())

            resolve("lighthouse").mkdirs()

            resolve("lighthouse/suites.json").writeText("""
            {
              "suites": [
                {
                  "name": "youtube",
                  "baseUrl": "https://www.youtube.com",
                  "paths": [
                    "/",
                    "/feed/trending"
                  ],
                  "args": [
                    "--config-path=lighthouse/config.json",
                    "--performance=90",
                    "--accessibility=90",
                    "--best-practices=80",
                    "--seo=60",
                    "--pwa=30"
                  ]
                },
                {
                  "name": "facebook",
                  "baseUrl": "https://www.facebook.com",
                  "paths": [
                    "/"
                  ],
                  "args": [
                    "--config-path=lighthouse/config.json",
                    "--performance=90",
                    "--accessibility=90",
                    "--best-practices=80",
                    "--seo=60",
                    "--pwa=30"
                  ]
                }
              ]
            }
            """.trimIndent())

            resolve("lighthouse/config.json").writeText("""
            {
              "extends": "lighthouse:default"
            }
            """.trimIndent())
        }

        // when
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("lighthouseRun")
        runner.withProjectDir(projectDir)
        val result = runner.build();

        // then
        assertEquals(result.task(":lighthouseRun")?.outcome, TaskOutcome.SUCCESS)
    }
}
