package ls.yylx.lsmediaeditor

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.Camera
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DCIM
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.camera_record_fragment.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors


class CameraRecordFragment : BaseAppCompatDialogFragment() {
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

    val imageCapture by lazy {
        ImageCapture.Builder().setTargetRotation(requireView().display.rotation).build()
    }

    val outputOptions by lazy {
        ImageCapture.OutputFileOptions.Builder(photoFile).build()
    }

    val imageAnalysis by lazy {
        ImageAnalysis.Builder()
            .setTargetResolution(Size(720, 1280))
            .build()
    }



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
        return inflater.inflate(R.layout.camera_record_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aib_record.run {
            clipToOutline = true
            setOnClickListener {
                takePhoto()
            }
            setOnLongClickListener {
//                mediaCodec?.start()
                true
            }
        }

        checkAllMediaPms {
            if (!it.containsValue(false)) {
                Log.e("camera", "checkAllMediaPms")
                startCamera()
            }
        }
    }

    private fun startCamera() {
        Log.e("camera", "startCamera")

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(preview_view.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalysis
                )
            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        imageAnalysis.setAnalyzer(cameraExecutor, { imageProxy: ImageProxy ->
            //预览过程中，如果需要录制视频，则在这里获取。
            //将图片转换成yuv(格式YUV_420_888)数据，再进行编码。
            //如果处理数据太慢，导致造成阻塞，可以设置监听器，把数据发送到其他线程进行处理


            Logger.e("""
                |${imageProxy.width}
                |${imageProxy.height}
                |${imageProxy.planes.map { it.buffer }}
                ||${imageProxy}
            """.trimMargin())

            val yBuffer = imageProxy.planes[0].buffer // Y
            val vuBuffer = imageProxy.planes[2].buffer // VU

            val ySize = yBuffer.remaining()
            val vuSize = vuBuffer.remaining()

            val nv21 = ByteArray(ySize + vuSize)

            yBuffer.get(nv21, 0, ySize)
            vuBuffer.get(nv21, ySize, vuSize)

            val yuvImage = YuvImage(nv21, ImageFormat.NV21, imageProxy.width, imageProxy.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
            val imageBytes = out.toByteArray()

            val bitmap= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            imageProxy.close()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    fun takePhoto() {

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("onError", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"

                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        Log.d("onImageSaved", msg)
                    }

//                    // We can only change the foreground Drawable using API level 23+ API
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        // Update the gallery thumbnail with latest picture taken
//                        setGalleryThumbnail(savedUri)
//                    }

                    // Implicit broadcasts will be ignored for devices running API level >= 24
                    // so if you only target API level 24+ you can remove this statement
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        requireActivity().sendBroadcast(
                            Intent(Camera.ACTION_NEW_PICTURE, savedUri)
                        )
                    }

                    // If the folder selected is an external media directory, this is
                    // unnecessary but otherwise other apps will not be able to access our
                    // images unless we scan them using [MediaScannerConnection]
                    val mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(savedUri.toFile().extension)
                    MediaScannerConnection.scanFile(
                        context,
                        arrayOf(savedUri.toFile().absolutePath),
                        arrayOf(mimeType)
                    ) { _, uri ->
                        Log.d("scanFile", "Image capture scanned into media store: $uri")
                    }


                }
            })
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
        )
    }

    companion object {
        var cacheName = "CameraRecord"
    }

}