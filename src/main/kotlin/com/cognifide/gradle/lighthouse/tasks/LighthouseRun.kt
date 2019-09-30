package com.cognifide.gradle.lighthouse.tasks

import com.cognifide.gradle.lighthouse.LighthouseExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

open class LighthouseRun : DefaultTask() {

    @Internal
    val lighthouse = LighthouseExtension.get(project)

    @TaskAction
    fun run() {
        lighthouse.runner { runSuites() }
    }
}
