import com.gradleup.librarian.gradle.librarianModule

plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.compose")
}

librarianModule(true)

android {
  namespace = "com.apollographql.compose"
  compileSdk = 34

  defaultConfig {
    minSdk = 21
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
  }

  testOptions.targetSdk = 30

  buildFeatures {
    compose = true
  }
}

dependencies {
  api(libs.compose.runtime)
  api(libs.apollo.runtime)
  api(libs.apollo.normalized.cache)
}

