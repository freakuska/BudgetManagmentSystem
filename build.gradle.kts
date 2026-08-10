plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    kotlin("android") version "1.9.10" apply false
    kotlin("kapt") version "1.9.10" apply false
    kotlin("plugin.serialization") version "1.9.10" apply false
}

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}
