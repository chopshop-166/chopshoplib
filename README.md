chopshoplib
===========

[![Chopshoplib on jitpack](https://jitpack.io/v/com.chopshop166/chopshoplib.svg)](https://jitpack.io/#com.chopshop166/chopshoplib)

Usage
-----

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

Version Info
------------

To generate version information in a format that can be read by `DashboardUtils.logTelemetry`, add the following to your `build.gradle`:

```groovy
def runCommand = { String... args ->
    def stdout = new ByteArrayOutputStream()
    exec {
        commandLine args
        standardOutput = stdout
    }
    return stdout.toString().trim()

}

def getGitHash = { -> runCommand "git", "describe", "--always" }

def getGitBranch = { -> runCommand "git", "rev-parse", "--abbrev-ref", "HEAD" }

def getGitFilesChanged = { -> runCommand "git", "diff", "--name-only", "HEAD" }

task versionTxt() {
    doLast {
        String resourcesDir = "$projectDir/src/main/resources"
        def logDirBase = new File(resourcesDir)
        logDirBase.mkdirs()
        new File("$resourcesDir/branch.txt").text = getGitBranch()
        new File("$resourcesDir/commit.txt").text = getGitHash()
        new File("$resourcesDir/changes.txt").text = getGitFilesChanged()
        new File("$resourcesDir/buildtime.txt").text =
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
    }
}

compileJava.dependsOn versionTxt
```

It's recommended that `src/main/resources` already exists, and contains the following gitconfig:

```gitconfig
*.txt
```