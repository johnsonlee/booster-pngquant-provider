plugins {
    kotlin("jvm") version "1.3.41"
    kotlin("kapt") version "1.3.21"
    `java-library`
    `maven-publish`
    `signing`

    id("io.codearte.nexus-staging") version "0.21.2"
    id("de.marcphilipp.nexus-publish") version "0.4.0"
}

group = "io.johnsonlee.booster"
version = "1.6.0"


repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    kapt("com.google.auto.service:auto-service:1.0-rc4")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.auto.service:auto-service:1.0-rc4")
    implementation("com.didiglobal.booster:booster-command:1.6.0")
    implementation("com.didiglobal.booster:booster-kotlinx:1.6.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
    classifier = "javadoc"
    from(tasks["javadoc"])
}

val OSSRH_USERNAME = project.properties["OSSRH_USERNAME"] as? String ?: System.getenv("OSSRH_USERNAME")
val OSSRH_PASSWORD = project.properties["OSSRH_PASSWORD"] as? String ?: System.getenv("OSSRH_PASSWORD")

nexusStaging {
    packageGroup = "io.johnsonlee"
    username = OSSRH_USERNAME
    password = OSSRH_PASSWORD
    numberOfRetries = 50
    delayBetweenRetriesInMillis = 3000
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(OSSRH_USERNAME)
            password.set(OSSRH_PASSWORD)
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            groupId = "${project.group}"
            artifactId = project.name
            version = "${project.version}"

            from(components["java"])

            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom.withXml {
                asNode().apply {
                    appendNode("name", project.name)
                    appendNode("url", "https://github.com/johnsonlee/booster-pngquant-provider")
                    appendNode("description", project.description ?: project.name)
                    appendNode("scm").apply {
                        appendNode("connection", "scm:git:git://github.com/johnsonlee/booster-pngquant-provider.git")
                        appendNode("developerConnection", "scm:git:git@github.com:johnsonlee/booster-pngquant-provider.git")
                        appendNode("url", "https://github.com/johnsonlee/booster-pngquant-provider")
                    }
                    appendNode("licenses").apply {
                        appendNode("license").apply {
                            appendNode("name", "GPL License")
                            appendNode("url", "https://www.gnu.org/licenses/gpl-3.0.txt")
                        }
                    }
                    appendNode("developers").apply {
                        appendNode("developer").apply {
                            appendNode("id", "johnsonlee")
                            appendNode("name", "Johnson Lee")
                            appendNode("email", "g.johnsonlee@gmail.com")
                        }
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
