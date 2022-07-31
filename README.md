# Snappy-android
An android port for snappy, based on snappy-java.
This project can be used as a drop-in replacement for snappy-java.


## Versioning
Snappy-android versioning scheme follows the versions of the snappy library.
E.g. Snappy-android `1.1.9` provides snappy library version `1.1.9`.  
Our Java bindings are compiled for Java 11.  
Our Android bindings are compiled for `minSdk=29, targetSdk=32, compileSdk=32`.


## Depending on this project
We publish [releases](https://github.com/Sebastiaan-Alvarez-Rodriguez/snappy-android/releases).
on jitpack.

In your project root `build.gradle`:
```groovy
allprojects {
    repositories {
        // Other repositories...
        maven { url 'https://jitpack.io' }
    }
}
```

In your module `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.Sebastiaan-Alvarez-Rodriguez:snappy-android:1.1.9'
}
```


## Using this project
This project can be used as a drop-in replacement for snappy-java.
Its usage is equivalent.
See snappy-java's usage [here](https://github.com/xerial/snappy-java/#usage).


## FAQ
Q: Can I use this project for non-Android (e.g. normal Java, iOS) projects?  
A: No, this project produces an Android Archive (AAR) binary, which only works for android. 

Q: Why do you produce an AAR instead of a Java Archive (JAR) binary?  
A: TL;DR We are shipping native libraries so we must do that.
The building process for Android PacKages (APK) and Android Bundles
requires that native libraries are present in AAR files in `/jni/<ABI>/libmylibrary.so`.
If we pass an AAR with stuff in `/jni/<ABI>/`, the build process copies the shared libraries
to `/jni/<ABI>/` of the output  APK/Bundle.
The Android system can then load these libraries at runtime using `System.loadLibrary("mylibrary")`.  
If we present a JAR file to the build process instead, with `/jni/<ABI>/libmylibrary.so` inside the JAR,
it places the entire JAR file in `/libs/myjar.jar`.
The Android system cannot find the libraries at `/jni/<ABI>`, and native libraries are not loaded.
So, we need an AAR.
For more info about the workings of AARs, see [here](https://developer.android.com/studio/projects/android-library.html#aar-contents).


## Thanks
Thanks to [snappy](https://github.com/google/snappy) by google et al. 
We use provide their library for android devices.

Thanks to [snappy-java](https://github.com/xerial/snappy-java/) by xerial et al. 
We used their java port as a foundation for our android port.