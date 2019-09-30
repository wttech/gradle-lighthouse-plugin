package com.cognifide.gradle.lighthouse

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull


class LighthousePluginTest {

    @Test fun `plugin registers task`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.cognifide.lighthouse")

        assertNotNull(project.tasks.findByName("lighthouseRun"))
    }

}
