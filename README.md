chopshoplib
===========

To use, in your `build.gradle` add the following:

```groovy
repositories {
    mavenCentral()
    maven {
        name "ChopShopLib"
        url "https://chopshop-166.github.io/chopshoplib/"
    }
}

dependencies {
    compile wpilib()
    compile ctre()
    compile navx()
    compile group: 'frc.team166', name: 'chopshoplib', version: '2019.0.0-alpha2', changing: true
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
