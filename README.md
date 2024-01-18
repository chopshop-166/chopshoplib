chopshoplib
===========

[![Chopshoplib on jitpack](https://jitpack.io/v/com.chopshop166/chopshoplib.svg)](https://jitpack.io/#com.chopshop166/chopshoplib)
[![Build Status](https://github.com/chopshop-166/chopshoplib/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/chopshop-166/chopshoplib/actions)

Usage
-----

For documentation on classes, see the [javadocs](https://jitpack.io/com/github/chopshop-166/chopshoplib/latest/javadoc/)

To use, in your `build.gradle` add the following:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile group: 'com.chopshop166.chopshoplib', name: 'core', version: '2024.1.1'
}
```

See [The release page](https://github.com/chopshop-166/chopshoplib/releases) for a list showing the latest tags, or use the latest located in the badge above.

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
