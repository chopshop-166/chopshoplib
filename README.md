chopshoplib
===========

[![Chopshoplib on jitpack](https://jitpack.io/v/com.chopshop166/chopshoplib.svg)](https://jitpack.io/#com.chopshop166/chopshoplib)

For documentation on classes, see the [javadocs](https://jitpack.io/com/github/chopshop-166/chopshoplib/latest/javadoc/)

To use, in your `build.gradle` add the following:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile group: 'com.chopshop166', name: 'chopshoplib', version: '2019.0.0-alpha6'
}
```

To use a local development version:

```groovy
repositories {
    mavenLocal()
}

dependencies {
    compile group: 'com.chopshop166', name: 'chopshoplib', version: 'unspecified'
}
```

Then run `./gradlew publishToMavenLocal` in this project before use in your robot project.
