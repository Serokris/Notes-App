buildscript {
    extra.apply {
        set("kotlinVersion", "1.5.31")
        set("composeVersion", "1.0.5")
        set("roomVersion", "2.4.0")
        set("daggerVersion", "2.39.1")
        set("jUnitVersion", "4.13.2")
        set("androidJUnitExtension", "1.1.3")
    }

    val daggerVersion: String by extra
    val kotlinVersion: String by extra

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$daggerVersion")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}