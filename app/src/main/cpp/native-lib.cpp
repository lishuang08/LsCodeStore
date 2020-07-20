#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_ls_yylx_lscodestore_ui_ndk_NdkMainFragment_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}