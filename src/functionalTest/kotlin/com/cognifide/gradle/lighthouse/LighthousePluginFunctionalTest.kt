package com.cognifide.gradle.lighthouse

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class LighthousePluginFunctionalTest {

    @Test
    fun `can run default suite`() {
        // given
        val projectDir = configureProjectDir()

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

    @Test
    fun `can run suite by name`() {
        // given
        val projectDir = configureProjectDir()

        // when
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("lighthouseRun", "-Plighthouse.suite=facebook")
        runner.withProjectDir(projectDir)
        val result = runner.build();

        // then
        assertEquals(result.task(":lighthouseRun")?.outcome, TaskOutcome.SUCCESS)
    }

    private fun configureProjectDir(): File {
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
                      "default": true,
                      "baseUrl": "https://www.youtube.com",
                      "paths": [
                        "/",
                        "/feed/trending"
                      ],
                      "args": [
                        "--config-path=lighthouse/config.json",
                        "--performance=30",
                        "--accessibility=30",
                        "--best-practices=30",
                        "--seo=30",
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
                        "--performance=30",
                        "--accessibility=30",
                        "--best-practices=30",
                        "--seo=30",
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
        return projectDir
    }
}
