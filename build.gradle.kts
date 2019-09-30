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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    plugins {
        create("lighthouse") {
            id = "com.cognifide.lighthouse"
            implementationClass = "com.cognifide.gradle.lighthouse.LighthousePlugin"
        }
    }
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
    withType<Test>().configureEach {
        testLogging.showStandardStreams = true
    }
}

