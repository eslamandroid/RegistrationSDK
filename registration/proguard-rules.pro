# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# Keep Compose-related classes
-keep class androidx.compose.** { *; }
-keep class androidx.compose.material.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class **$$composable { *; }
-keep @androidx.compose.ui.tooling.preview.Preview class * { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Keep Room Database-related classes
-keep class androidx.room.Room { *; }
-keep class * extends androidx.room.RoomDatabase
-keep class android.arch.** { *; }
-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }

# Keep Lifecycle and Coroutines
-keep class androidx.lifecycle.** { *; }
-keep class kotlinx.coroutines.** { *; }

# Keep SQLCipher classes
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }
-dontwarn net.sqlcipher.**

# Keep Kotlin-related metadata
-keepattributes KotlinMetadata
-keep class kotlin.Metadata { *; }

# Keep Hilt dependency injection
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManager { *; }
-keep class * extends dagger.hilt.components.SingletonComponent { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep,allowobfuscation,allowshrinking @dagger.hilt.EntryPoint class *
-keep,allowobfuscation,allowshrinking @dagger.hilt.android.EarlyEntryPoint class *
-keep @dagger.hilt.android.internal.** class *
-keep @dagger.hilt.** class *
-keep @com.google.auto.value.** class *


# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel

-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.ViewModelProvider { *; }
-keep class androidx.lifecycle.ViewModelStore { *; }


# Keep Valify Registration SDK
-keep class com.valify.registrationsdk.data.di.** { *; }
-keep class com.valify.registrationsdk.presentation.ui.registration.viewmodel.** { *; }
-keep class com.valify.registrationsdk.presentation.ui.captureImage.viewmodel.** { *; }
-keep class com.valify.registrationsdk.domain.** { *; }
-keep class com.valify.registrationsdk.presentation.MainActivity { *; }
-keep class com.valify.registrationsdk.presentation.MainActivity_GeneratedInjector { *; }
-keep class com.valify.registrationsdk.projectone.Hilt_* { *; }

# Keep Serializable and Parcelable classes
-keepclassmembers class * implements java.io.Serializable { *; }
-keepclassmembers class * implements android.os.Parcelable { *; }

# Keep annotation attributes
-keepattributes *Annotation*

# General rules
-dontwarn java.lang.invoke.StringConcatFactory
-dontwarn androidx.room.paging.**


