// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply("dependencies.gradle")
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://jitpack.io") }
    }
    dependencies {
        classpath(GradlePlugin.gradle)
        classpath(GradlePlugin.kotlin)
        classpath(GradlePlugin.googleServices)
        classpath(GradlePlugin.crashlytics)
        //classpath(GradlePlugin.daggerHilt)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("quickfire.enableComposeCompilerReports") == "true") {
                kotlinOptions.freeCompilerArgs += listOf(
                    "-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.buildDir.absolutePath + "/compose_metrics"
                )

                kotlinOptions.freeCompilerArgs += listOf(
                    "-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.buildDir.absolutePath + "/compose_metrics"
                )
            }
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
