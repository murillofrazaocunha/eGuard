plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
repositories {
    mavenCentral()
}
subprojects {
    group = "com.eomuhzin.guard"
    version = "1.0-SNAPSHOT"
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }



}
tasks.register("printJavaVersion") {
    doLast {
        println("Java usado: " + System.getProperty("java.version"))
        println("Java home: " + System.getProperty("java.home"))
    }
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}
