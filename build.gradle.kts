plugins {
    `java-gradle-plugin`
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${properties["kotlin.version"]}"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${properties["kotlin.version"]}")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${properties["kotlin.version"]}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${properties["kotlin.version"]}")
}

gradlePlugin {
    val lighthouse by plugins.creating {
        id = "com.cognifide.lighthouse"
        implementationClass = "com.cognifide.gradle.lighthouse.LighthousePlugin"
    }
}

val functionalTestSourceSet = sourceSets.create("functionalTest") {}
val testSourceSet = sourceSets["test"]

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
    withType<Test>().configureEach {
        testLogging.showStandardStreams = true
    }
}

