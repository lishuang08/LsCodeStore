//
// Created by ls on 2020/11/27.
//

#include <oboe/Oboe.h>
#include <jni.h>


oboe::AudioStreamBuilder builder;
class MyCallback : public oboe::AudioStreamDataCallback {
public:
    oboe::DataCallbackResult
    onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) override {

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

MyCallback *MyCallback;

extern "C" JNIEXPORT void JNICALL
Java_ls_yylx_lsmediaeditor_OboeEngine_native_1setAudioApi(JNIEnv *env, jobject thiz,
                                                          jlong engine_handle, jint audio_api) {


    builder.setDirection(oboe::Direction::Output);
    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setSharingMode(oboe::SharingMode::Exclusive);
    builder.setFormat(oboe::AudioFormat::Float);
    builder.setChannelCount(oboe::ChannelCount::Mono);

    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency)
            ->setSharingMode(oboe::SharingMode::Exclusive)
            ->setDataCallback(MyCallback)
            ->setFormat(oboe::AudioFormat::Float);

}


