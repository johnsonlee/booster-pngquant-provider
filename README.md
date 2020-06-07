# booster-pngquant-provider

This project is a plugin of [booster](https://github.com/didi/booster) to provide prebuilt pngquant executables

## Getting Started

```gradle
buildscript {
    ext {
        booster_version = "1.7.2"
    }
    ...
    dependencies {
        classpath "com.didiglobal.booster:booster-gradle-plugin:$booster_version"
        ...
        classpath "com.didiglobal.booster:booster-task-compression-pngquant:$booster_version"
        classpath "io.johnsonlee.booster:booster-pngquant-provider:$booster_version"
    }
    ...
}
```
