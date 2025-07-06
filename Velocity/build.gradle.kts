repositories {
    mavenCentral()
    maven("https://nexus.velocitypowered.com/repository/maven-public")
}
dependencies {
    implementation(project(":Commons"))
    compileOnly(files(rootDir.resolve("libs/velocity-proxy-3.4.0-SNAPSHOT-all.jar")))

}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
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
    archiveFileName.set("eGuard-Velocity-${project.version}-all.jar")
}
tasks.processResources {
    filesMatching("velocity-plugin.json") { expand("project" to project) }
}