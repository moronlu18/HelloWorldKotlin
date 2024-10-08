import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.jetbrains.dokka) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}
