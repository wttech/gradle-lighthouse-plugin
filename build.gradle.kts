import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.dokka")
    id("io.gitlab.arturbosch.detekt")
    id("net.researchgate.release")
}

defaultTasks("build", "publishToMavenLocal")
group = "com.cognifide.gradle"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${properties["kotlin.version"]}"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.8")
    implementation("commons-io:commons-io:2.6")
    implementation("com.github.node-gradle:gradle-node-plugin:3.1.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:${properties["detekt.version"]}")
}


val functionalTestSourceSet = sourceSets.create("functionalTest") {}
gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations.getByName("functionalTestImplementation").extendsFrom(configurations.getByName("testImplementation"))

tasks {
    register<Test>("functionalTest") {
        testClassesDirs = functionalTestSourceSet.output.classesDirs
        classpath = functionalTestSourceSet.runtimeClasspath
    }
    named<Task>("check") {
        dependsOn("functionalTest")
    }
    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        dependsOn("classes")
        from(sourceSets["main"].allSource)
    }
    register<DokkaTask>("dokkaJavadoc") {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"
    }
    register<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaJavadoc")
        from("$buildDir/javadoc")
    }
    withType<Test>().configureEach {
        testLogging.showStandardStreams = true
    }
    named("afterReleaseBuild") {
        dependsOn("publishPlugins")
    }
    named("updateVersion") {
        enabled = false
    }
}

detekt {
    config.from(file("detekt.yml"))
    parallel = true
    autoCorrect = true
    failFast = true
}

gradlePlugin {
    plugins {
        create("lighthouse") {
            id = "com.cognifide.lighthouse"
            implementationClass = "com.cognifide.gradle.lighthouse.LighthousePlugin"
            displayName = "Lighthouse Plugin"
            description = "Runs Lighthouse tests on multiple sites / web pages with checking thresholds (useful on continuous integration, constant performance checking)."
        }
    }
}

pluginBundle {
    website = "https://github.com/wttech/gradle-lighthouse-plugin"
    vcsUrl = "https://github.com/wttech/gradle-lighthouse-plugin.git"
    description = "Gradle Lighthouse Plugin"
    tags = listOf("lighthouse", "performance", "test", "seo", "pwa")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}
