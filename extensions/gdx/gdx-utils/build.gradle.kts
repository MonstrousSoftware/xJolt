plugins {
    //id("java")
    id("java-library")
}

val moduleName = "gdx-utils"

dependencies {
    implementation("com.badlogicgames.gdx:gdx:${LibExt.gdxVersion}")
    if(LibExt.useRepoLibs) {
        api("com.github.xpenatan.xJolt:jolt-core:-SNAPSHOT")
        //api("com.github.xpenatan.xJolt:gdx-utils:-SNAPSHOT")
    }
    else {
        api(project(":jolt:jolt-core"))
//        implementation(project(":extensions:gdx:gdx-utils"))
    }
}

java {
    sourceCompatibility = JavaVersion.toVersion(LibExt.java8Target)
    targetCompatibility = JavaVersion.toVersion(LibExt.java8Target)
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = moduleName
            group = LibExt.groupId
            version = LibExt.libVersion
            from(components["java"])
        }
    }
}