import java.util.Properties

plugins {
    id("com.android.application")
}

fun getIpAddress(): String? {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    return properties.getProperty("ip_addr")
}

    android {
        namespace = "com.example.bookingapp"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.example.bookingapp"
            minSdk = 24
            targetSdk = 33
            versionCode = 1
            versionName = "1.0"
            buildConfigField("String", "IP_ADDR", "\""+getIpAddress()+"\"")
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            multiDexEnabled = true
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        buildFeatures {
            viewBinding = true
            compose = true
            buildConfig = true

        }

    }


    dependencies {

        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.compose.animation:animation-core-android:1.5.4")
        //for map
        implementation("com.google.android.gms:play-services-maps:18.2.0")
        //for carousel
        implementation("com.github.bumptech.glide:glide:4.16.0")
        //for calendar
        implementation("com.applandeo:material-calendar-view:1.9.0-rc04")
        //for graph
        implementation("com.diogobernardino:williamchart:3.10.1")
        //for testing
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.17")
        // for server connection
        implementation("com.google.code.gson:gson:2.8.7")
        implementation("com.squareup.retrofit2:retrofit:2.3.0")
        implementation("com.squareup.retrofit2:converter-gson:2.3.0")
        implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")
        implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
        implementation("io.reactivex.rxjava2:rxjava:2.1.1")
        implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")

    }


