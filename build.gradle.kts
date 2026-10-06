plugins {
    java
    id("com.gradleup.shadow") version "9.6.1"
}

group = "fr.dianox"
version = "1.3.0-Beta"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/releases/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

// Compile-time stubs for plugins without a public Maven repository (never shipped).
val stubs: SourceSet = sourceSets.create("stubs")

// Newest server API, only used by the "checkLatestApi" task
val latestApi: Configuration = configurations.create("latestApi")

dependencies {
    // Oldest supported API: the plugin runs from 1.16.5 up to the latest releases.
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    "stubsCompileOnly"("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly(stubs.output)

    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4") { isTransitive = false }
    compileOnly("com.sk89q.worldguard:worldguard-core:7.0.4") { isTransitive = false }
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0") { isTransitive = false }
    compileOnly("com.sk89q.worldedit:worldedit-core:7.2.0") { isTransitive = false }

    latestApi("io.papermc.paper:paper-api:26.3.build.141-beta")

    // Shaded & relocated
    implementation("com.github.cryptomorin:XSeries:13.7.1")
    implementation("fr.mrmicky:fastboard:2.2.2")
    implementation("org.bstats:bstats-bukkit:3.2.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    // Compiles the plugin against the newest Paper API to catch removed or changed methods.
    // Usage: ./gradlew checkLatestApi
    register<JavaCompile>("checkLatestApi") {
        group = "verification"
        description = "Compiles Hawn against the latest Paper API"
        source = sourceSets.main.get().java
        classpath = files(latestApi, stubs.output) + configurations.compileClasspath.get().filter {
            !it.name.startsWith("spigot-api") && !it.name.startsWith("bungeecord-chat")
        }
        destinationDirectory.set(layout.buildDirectory.dir("checkLatestApi"))
        javaCompiler.set(project.javaToolchains.compilerFor { languageVersion.set(JavaLanguageVersion.of(25)) })
        options.encoding = "UTF-8"
        options.release.set(25)
        options.compilerArgs.addAll(listOf("-Xlint:removal", "-Xmaxwarns", "1000"))
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        // Java 8 bytecode so the jar still loads on 1.16.5 servers running Java 8/11.
        options.release.set(8)
        options.compilerArgs.add("-Xlint:-options")
    }

    processResources {
        filteringCharset = "UTF-8"
        // Without it, Gradle keeps the old plugin.yml when only the version changes
        inputs.property("version", project.version)
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("Hawn-${project.version}.jar")
        val base = "fr.dianox.hawn.libs"
        relocate("com.cryptomorin.xseries", "$base.xseries")
        relocate("fr.mrmicky.fastboard", "$base.fastboard")
        relocate("org.bstats", "$base.bstats")
        minimize {
            exclude(dependency("com.github.cryptomorin:XSeries:.*"))
        }
        exclude("META-INF/maven/**", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }
}
