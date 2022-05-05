import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.protobuf")
    id("maven-publish")
}

group = "com.imversed"
version = "1.0.1"

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31
        aarMetadata {
            minCompileSdk = 24
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        abortOnError = false
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    sourceSets.getByName("main") {
        proto {
            srcDir("src/main/protobuf")
        }
    }
    sourceSets.getByName("debug") {
        java {
            srcDirs(
                "build/generated/source/proto/debug/grpc",
                "build/generated/source/proto/debug/grpckt",
                "build/generated/source/proto/debug/kotlin",
                "build/generated/source/proto/debug/java"
            )
        }
    }
    sourceSets.getByName("release") {
        java {
            srcDirs(
                "build/generated/source/proto/release/grpc",
                "build/generated/source/proto/release/grpckt",
                "build/generated/source/proto/release/kotlin",
                "build/generated/source/proto/release/java"
            )
        }
    }
}

publishing {
    repositories {
        maven {
            name = "release"
            url = uri("file:" + System.getenv("MAVEN_REPOSITORY_PATH"))
        }
    }
    publications {
        register<MavenPublication>("release") {
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
        }
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.45.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.1:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("java")
                id("kotlin")
            }
        }
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-kotlin:3.19.4")
    api("com.google.protobuf:protobuf-java-util:3.19.4")
    api("io.grpc:grpc-protobuf:1.45.0")
    api("io.grpc:grpc-okhttp:1.45.0")
    api("io.grpc:grpc-stub:1.45.0")
    api("io.grpc:grpc-kotlin-stub:1.2.1")
}
