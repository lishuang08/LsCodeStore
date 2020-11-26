package ls.yylx.lsmediaeditor

import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DCIM
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraView
import androidx.camera.view.PreviewView
import androidx.camera.view.video.OnVideoSavedCallback
import androidx.camera.view.video.OutputFileResults
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.camera_view_fragment.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors


class CameraViewFragment : BaseAppCompatDialogFragment() {
    private val crVm by viewModels<CameraRecordViewModel>()


    private val cameraExecutor by lazy {
        Executors.newCachedThreadPool()
    }

//    val mediaCodec by lazy {
//        getVideoMediaCodec()
//    }
//
//    private fun getVideoMediaCodec(): MediaCodec? {
//        val format: MediaFormat = MediaFormat.createVideoFormat("video/av01", 720, 1280)
//        //设置颜色格式
//        format.setInteger(
//            MediaFormat.KEY_COLOR_FORMAT,
//            MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
//        )
//        //设置比特率(设置码率，通常码率越高，视频越清晰)
//        format.setInteger(MediaFormat.KEY_BIT_RATE, 720 * 1280)
//        //设置帧率
//        format.setInteger(MediaFormat.KEY_FRAME_RATE, 10)
//        //关键帧间隔时间，通常情况下，你设置成多少问题都不大。
//        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 10)
//        // 当画面静止时,重复最后一帧，不影响界面显示
//        format.setLong(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, 1000000 / 45)
//        format.setInteger(
//            MediaFormat.KEY_BITRATE_MODE,
//            MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR
//        )
//        //设置复用模式
//        format.setInteger(
//            MediaFormat.KEY_COMPLEXITY,
//            MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR
//        )
//        var mediaCodec: MediaCodec? = null
//        try {
//            mediaCodec = MediaCodec.createEncoderByType("video/av01")
//            mediaCodec.configure(
//                format,
//                null  ,
//                null,
//                MediaCodec.CONFIGURE_FLAG_ENCODE
//            )
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//            if (mediaCodec != null) {
//                mediaCodec.reset()
//                mediaCodec.stop()
//                mediaCodec.release()
//                mediaCodec = null
//            }
//        }
//        return mediaCodec
//    }


//    val videoCapture by lazy {
//        VideoCapture.Builder().build()
//    }
//
//    val outputVideoOptions by lazy {
//        VideoCapture.OutputFileOptions.Builder(photoFile).build()
//    }

    val videoFile
        get() = File(
            outputDirectory,
            SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss-SSS", Locale.CHINA
            ).format(System.currentTimeMillis()) + ".mp4"
        )

    val photoFile
        get() = File(
            outputDirectory,
            SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss-SSS", Locale.CHINA
            ).format(System.currentTimeMillis()) + ".jpg"
        )

    private lateinit var outputDirectory: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        outputDirectory = getOutputDirectory()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camera_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        checkCamera {
            if (it) {
                camera_view.run {
                    captureMode = CameraView.CaptureMode.VIDEO
                    cameraLensFacing = CameraSelector.LENS_FACING_BACK
                    hasCameraWithLensFacing(CameraSelector.LENS_FACING_BACK)
                    enableTorch(true)
                    isPinchToZoomEnabled = true
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                camera_view.bindToLifecycle(this)
            }
        }


        aib_record.run {
            clipToOutline = true
            setOnClickListener {
                if (camera_view.isRecording()) {
                    camera_view.stopRecording()


                } else {
                    camera_view.startRecording(
                        videoFile,
                        cameraExecutor,
                        object : OnVideoSavedCallback {
                            override fun onVideoSaved(outputFileResults: OutputFileResults) {

                                snack(outputFileResults.savedUri.toString())
                            }

                            override fun onError(
                                videoCaptureError: Int,
                                message: String,
                                cause: Throwable?
                            ) {
                                snack("保存错误")
                            }

                        })
                }
            }
            setOnLongClickListener {
//                mediaCodec?.start()
                true
            }
        }

        checkAllMediaPms {
            if (!it.containsValue(false)) {
                Log.e("camera", "checkAllMediaPms")

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        camera_view.stopRecording()
    }


    private fun getOutputDirectory(): File {
        val mediaDir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM)?.let {
            File(it, cacheName).apply {
                mkdirs()
            }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else File(
            requireContext().externalCacheDir,
            cacheName
        ).apply {
            if (!exists()) mkdirs()
        }
    }

    companion object {
        var cacheName = "CameraRecord"
    }

}