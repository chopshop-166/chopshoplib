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
    maven {
        name "Development ChopShopLib"
        // This line should point to the location of chopshoplib
        url "file://${projectDir}/../chopshoplib/build/repo"
    }
}
```
