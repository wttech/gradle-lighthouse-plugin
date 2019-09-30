package com.cognifide.gradle.lighthouse

import com.cognifide.gradle.lighthouse.tasks.LighthouseRun
import org.gradle.api.Project
import org.gradle.api.Plugin

open class LighthousePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply("com.moowork.node")
        project.extensions.add("lighthouse", LighthouseExtension(project))
        project.tasks.register("lighthouseRun", LighthouseRun::class.java)
    }
}
