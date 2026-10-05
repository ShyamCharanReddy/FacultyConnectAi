# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\shyam\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# Serialization and client model reflection rules
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-dontwarn com.google.ai.client.generativeai.**
