plugins {
    java
    id("net.kyori.blossom") version "1.2.0"
    id("net.kyori.indra.git") version "2.1.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.utilityclient"
version = "1.0.0"

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    compileOnly("org.ow2.asm:asm:9.5")
    compileOnly("net.fabricmc:access-widener:2.1.0")
    compileOnly("net.fabricmc:tiny-remapper:0.8.2")
    compileOnly("net.fabricmc:tiny-mappings-parser:0.3.0+build.17")
    implementation("net.fabricmc:fabric-loader:0.14.22")
    shadow("net.fabricmc:fabric-loader:0.14.22")
}

blossom {
    replaceToken("%VERSION%", version)
    replaceToken("%LAST_COMMIT_HASH%", lastCommitHash())
}

fun lastCommitHash(): String =
    rootProject.indraGit.commit()?.name?.substring(0, 7)
        ?: "unknown"

tasks {
    shadowJar {
        fun reloc(pkg: String) = relocate(pkg, "org.utilityclient.loader.libs.$pkg")
        archiveClassifier.set("")
    }
    jar {
        manifest.attributes["Main-Class"] = "org.utilityclient.loader.Loader"
    }
    build {
        dependsOn(shadowJar)
    }
}