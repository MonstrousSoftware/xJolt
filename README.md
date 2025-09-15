# xJolt

![Build](https://github.com/xpenatan/xJolt/actions/workflows/release.yml/badge.svg)
![Build](https://github.com/xpenatan/xJolt/actions/workflows/snapshot.yml/badge.svg)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/releases/com.github.xpenatan.xJolt/jolt-core?nexusVersion=2&server=https%3A%2F%2Foss.sonatype.org&label=release)](https://repo.maven.apache.org/maven2/com/github/xpenatan/xJolt/)
[![Static Badge](https://img.shields.io/badge/snapshot---SNAPSHOT-red)](https://oss.sonatype.org/content/repositories/snapshots/com/github/xpenatan/xJolt/)

xJolt is a Java binding for the C++ library [Jolt Physics](https://github.com/jrouwe/JoltPhysics), utilizing JNI for desktop and mobile platforms, and Emscripten for web platforms. It provides a one-to-one correspondence with the C++ code, meaning it mirrors the exact same class and method names. Its primary focus is to support libGDX projects.

The binding leverages [jParser](https://github.com/xpenatan/jParser), a custom C/C++ build tool and WebIDL Java code generator, which automatically generates 99% of all classes. Only a small number of essential classes are coded manually, making updates to new Jolt Physics versions quick and efficient.

![image](https://github.com/user-attachments/assets/98ab1f09-6b00-4665-8082-40179f3fbf74)


## Web/TeaVM Examples:
* [Jolt-Samples](https://xpenatan.github.io/xJolt/examples/samples/)


### Platform status:

| Emscripten | Windows | Linux | Mac | Android | iOS |
|:----------:|:-------:|:-----:|:---:|:-------:|:---:|
|     ✅      | ✅ | ✅ |  ✅  | ✅ | ❌ |

* ✅: Have a working build.
* ❌: Build not ready.

## Setup
```groovy
// Use -SNAPSHOT" or any released git-tag version

// Add repository to Root gradle
repositories {
    mavenLocal()
    mavenCentral()
    maven { url "https://central.sonatype.com/repository/maven-snapshots/" }
}

// Core module
implementation("com.github.xpenatan.xJolt:jolt-core:-SNAPSHOT")
implementation("com.github.xpenatan.xJolt:gdx-utils:-SNAPSHOT")  // Gdx Shared utils
implementation("com.github.xpenatan.xJolt:gdx-gl:-SNAPSHOT")     // OpenGL
implementation("com.github.xpenatan.xJolt:gdx-wgpu:-SNAPSHOT")   // WebGPU

// Desktop module
dependencies {
   implementation("com.github.xpenatan.xJolt:jolt-desktop:-SNAPSHOT")
}

// TeaVM module
dependencies {
   implementation("com.github.xpenatan.xJolt:jolt-teavm:-SNAPSHOT")
}

// Android module
dependencies {
   implementation("com.github.xpenatan.xJolt:jolt-android:-SNAPSHOT")
}
```

## Notes
* In most classes, new instances are created using `JoltNew.[TYPE]` instead of standard Java constructors. This approach is necessary due to limitations in constructor overloading within WebIDL when targeting Emscripten.
* Methods that return an object typically return a temporary object. You should not retain a reference to it, as calling the method again with another instance will overwrite the previously returned object.
* Classes are not disposed automatically; the dispose method must be called when they are no longer in use. However, classes in a WebIDL file marked with "NoDelete" do not require disposal.

## Source Build Prerequisites

- Java 17 or later
- Gradle
- Android NDK ¹
- [Mingw64](https://github.com/niXman/mingw-builds-binaries/releases) or [Visual Studio C++](https://visualstudio.microsoft.com/vs/community/) ¹
- [Emscripten](https://emscripten.org/) ¹

¹: Only need if you want to build from source.

To try the samples with your build, change `LibExt.exampleUseRepoLibs` to false in `Dependencies.kt`. This will allow you to use the local Jolt source code instead of the remote repository.

## How to run the samples
- Clone the repository
- Run ./gradlew :examples:samples:desktop:jolt_samples_run_desktop


## How to build from source

```
./gradlew :jolt:jolt-build:jolt_download_source

### Build all targets if your machine can handle:
./gradlew :jolt:jolt-build:jolt_build_project_all

### Or a single platform target:
./gradlew :jolt:jolt-build:jolt_build_project_windows64
./gradlew :jolt:jolt-build:jolt_build_project_linux64
./gradlew :jolt:jolt-build:jolt_build_project_mac64 :jolt:jolt-build:build_project_macArm
./gradlew :jolt:jolt-build:jolt_build_project_teavm
```
