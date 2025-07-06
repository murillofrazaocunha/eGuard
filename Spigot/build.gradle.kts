repositories {
    maven(url = "https://repo.hpfxd.com/releases/")
}
dependencies {
    compileOnly("com.hpfxd.pandaspigot:pandaspigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation(project(":Commons"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}
// configuração para buildar o shadowJar com as deps certas
val shadowImplementation by configurations.creating {
    extendsFrom(configurations.implementation.get())
    isCanBeResolved = true
}

// configura o shadowJar
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(shadowImplementation)
    destinationDirectory.set(file("$rootDir/builds/"))

    // define o nome do jar como eGuard-VERSÃO-all.jar
    archiveFileName.set("eGuard-Spigot-${project.version}-all.jar")
}
tasks.processResources {
    filesMatching("plugin.yml") { expand("project" to project) }
}