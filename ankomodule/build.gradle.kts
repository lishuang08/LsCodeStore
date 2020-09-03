apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'


android {
    compileSdkVersion 30
    buildToolsVersion '30.0.1'

    defaultConfig {
        if (!isModule) {
            applicationId "ls.yylx.lscodestore.firstmodule"
        }

        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug {
            ext.enableCrashlytics = false
        }
    }

    compileOptions {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {
    implementation project(':basemodule')
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.2.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    //    /**核心库-anko库 ，dsl动态创建view*/


}