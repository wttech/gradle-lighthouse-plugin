package com.cognifide.gradle.lighthouse

import com.cognifide.gradle.lighthouse.tasks.LighthouseRun
import com.github.gradle.node.NodeExtension
import com.github.gradle.node.yarn.task.YarnInstallTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

open class LighthousePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply("com.github.node-gradle.node")

        val node = NodeExtension.get(project)

        node.apply {
            version.set("10.16.3")
            yarnVersion.set("1.19.0")
            download.set(true)
        }

        val yarnInstall = project.tasks.named(YarnInstallTask.NAME, YarnInstallTask::class.java) {
            File(node.nodeProjectDir.get().asFile, "package.json").apply {
                if (!exists()) {
                    writeText("""
                        {
                          "license": "UNLICENSED",
                          "devDependencies": {
                            "lighthouse-ci": "^1.12.0"
                          }
                        }
                    """.trimIndent())
                }
            }
        }

        project.extensions.add("lighthouse", LighthouseExtension(project))

        project.tasks.register("lighthouseRun", LighthouseRun::class.java) {
            it.dependsOn(yarnInstall)
        }
    }
}
