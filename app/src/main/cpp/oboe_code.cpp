//
// Created by 17687 on 2020/9/2.
//

#include "oboe_code.h"
#include <oboe/Oboe.h>
#include <jni.h>


class oboe_code {

};

extern "C" JNIEXPORT jstring
JNICALL Java_ls_yylx_lscodestore_ui_ndk_NdkMainFragment_playAudioFromJNI(JNIEnv *env,
                                                                         jobject thiz) {
    oboe::AudioStreamBuilder oboeBuilder;

    oboeBuilder.setDirection(oboe::Direction::Output);
    oboeBuilder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    oboeBuilder.setSharingMode(oboe::SharingMode::Exclusive);
    oboeBuilder.setFormat(oboe::AudioFormat::Float);
    oboeBuilder.setChannelCount(oboe::ChannelCount::Mono);
//    oboeBuilder.setCallback(&myCallback);
//
//
//    oboe::Result result;
//      result = oboeBuilder.openStream(&stream_);
//    if (result != OK){
//        __android_log_print(ANDROID_LOG_ERROR,
//                            "AudioEngine",
//                            "Error opening stream %s",
//                            convertToText(result));
//    }
}

class MyCallback : public oboe::AudioStreamCallback {
public:
    oboe::DataCallbackResult
    onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) {

        // We requested AudioFormat::Float so we assume we got it.
        // For production code always check what format
        // the stream has and cast to the appropriate type.
        auto *outputData = static_cast<float *>(audioData);

        // Generate random numbers (white noise) centered around zero.
        const float amplitude = 0.2f;
        for (int i = 0; i < numFrames; ++i){
            outputData[i] = ((float)drand48() - 0.5f) * 2 * amplitude;
        }

        return oboe::DataCallbackResult::Continue;
    }
};