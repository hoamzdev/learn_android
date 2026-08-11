import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.example.testBaseApp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.testBaseApp"
        minSdk = 29
        //noinspection OldTargetApi
        targetSdk = 36
        versionCode = 100
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf(
                "armeabi-v7a",
                "arm64-v8a",
                "x86",
                "x86_64"
            )
        }

    }

    val formattedDate = SimpleDateFormat("MM.dd.yyyy").format(Date())
    setProperty(
        "archivesBaseName",
        "DinhVanHoa${defaultConfig.versionName}_$formattedDate"
    )


    flavorDimensions += "default"
    productFlavors {
        create("for_dev") {
            dimension = "default"
        }

    }

    sourceSets {
        getByName("for_dev") {
            res.srcDirs("src/for_dev/res")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    bundle {
        language {
            enableSplit = false
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":app-domain"))
    implementation(project(":app-data"))
    implementation(project(":excel-viewer"))
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.lottie)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.dimenlib)
    implementation(libs.dimensplib)

    implementation(libs.androidx.webkit)
    implementation("com.github.skydoves:cloudy:0.1.2")//blur for jetpack
    implementation("jp.wasabeef:blurry:4.0.1")//chi dung cho image


    // Navigation Component cho Kotlin

    implementation("com.vladsch.flexmark:flexmark:0.64.8")

    implementation("com.google.android.flexbox:flexbox:3.0.0")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.multidex:multidex:2.0.1")

}
