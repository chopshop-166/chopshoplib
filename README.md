chopshoplib
===========

[![Chopshoplib on jitpack](https://jitpack.io/v/chopshop-166/chopshoplib.svg)](https://jitpack.io/#chopshop-166/chopshoplib)

To use, in your `build.gradle` add the following:

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile wpilib()
    compile ctre()
    compile navx()
    compile group: 'com.github.chopshop-166', name: 'chopshoplib', version: '2019.0.0-alpha4'
}
```

To use a local development version:

```groovy
repositories {
    mavenLocal()
}

dependencies {
    // For now, these are separate groups
    compile group: 'com.chopshop166', name: 'chopshoplib', version: 'unspecified'
}
```

Then run `./gradlew publishToMavenLocal` in this project before use in your robot project.
