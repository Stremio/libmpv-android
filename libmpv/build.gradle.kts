import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "dev.jdtech.mpv"
    compileSdk = 36
    buildToolsVersion = "37.0.0"
    ndkVersion = "29.0.14206865"

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("proguard-rules.pro")
        externalNativeBuild {
            cmake {
                arguments += listOf(
                    "-DANDROID_STL=c++_shared",
                )
                cFlags += "-Werror"
                cppFlags += "-std=c++11"
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "4.1.2"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    configure(
        platform = AndroidSingleVariantLibrary(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = SourcesJar.Empty(),
            variant = "release",
        )
    )

    coordinates(
        groupId = "com.github.Stremio",
        artifactId = "libmpv",
        version = "0.6.1"
    )

    pom {
        name = "libmpv-android"
        description = "libmpv for Android"
        inceptionYear = "2023"
        url = "https://github.com/stremio/libmpv-android"
        licenses {
            license {
                name = "MIT license"
                url = "https://github.com/stremio/libmpv-android/blob/main/LICENSE"
            }
        }
        developers {
            developer {
                id = "jarnedemeulemeester"
                name = "Jarne Demeulemeester"
                email = "jarnedemeulemeester@gmail.com"
            }
        }
        scm {
            url = "https://github.com/stremio/libmpv-android.git"
            connection = "scm:git@github.com:stremio/libmpv-android.git"
            developerConnection = "scm:git@github.com:stremio/libmpv-android.git"
        }
        issueManagement {
            system = "GitHub"
            url = "https://github.com/stremio/libmpv-android/issues"
        }
    }
}
