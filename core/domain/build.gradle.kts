plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.javax.inject)
}