package com.cognifide.gradle.lighthouse

import java.io.File
import org.gradle.testkit.runner.GradleRunner
import kotlin.test.Test
import kotlin.test.assertTrue

class LighthousePluginFunctionalTest {
    @Test fun `can run task`() {
        // given
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle.kts").writeText("")
        projectDir.resolve("build.gradle.kts").writeText("""
            plugins {
                id("com.cognifide.lighthouse")
            }
        """)

        // when
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("lighthouseRun")
        runner.withProjectDir(projectDir)
        val result = runner.build();

        // then
        assertTrue(result.output.contains("Hello from plugin 'com.cognifide.lighthouse'"))
    }
}
