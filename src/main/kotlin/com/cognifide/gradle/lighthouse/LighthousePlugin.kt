package com.cognifide.gradle.lighthouse

import org.gradle.api.Project
import org.gradle.api.Plugin

open class LighthousePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("lighthouseRun") { task ->
            task.doLast {
                println("Hello from plugin 'com.cognifide.lighthouse'")
            }
        }
    }
}
