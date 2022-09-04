plugins {
    kotlin("jvm") version "1.7.10"
}

group = "de.skycave"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation("org.mongodb:mongodb-driver-sync:4.7.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
