package ls.yylx.lscodestore.ffmpeg


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.base.BaseFragment
import ls.yylx.lscodestore.ext.checkSdPms
import org.jetbrains.anko.button
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.verticalLayout

class FfmpegFragment : BaseFragment() {
    val ffmpegVm by viewModels<FfmpegViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI {
        frameLayout {
//            customView<PreviewView> {
//
//                checkCamera {
//                    if (it) {
//                        camera_view.run {
//                            captureMode = CameraView.CaptureMode.VIDEO
//                            cameraLensFacing = CameraSelector.LENS_FACING_BACK
//                            hasCameraWithLensFacing(CameraSelector.LENS_FACING_BACK)
//                            enableTorch(true)
//                            isPinchToZoomEnabled = true
//                            scaleType = PreviewView.ScaleType.FILL_CENTER
//                        }
//                        camera_view.bindToLifecycle(this)
//                    }
//                }
//
//
//                aib_record.run {
//                    clipToOutline = true
//                    setOnClickListener {
//                        if (camera_view.isRecording()) {
//                            camera_view.stopRecording()
//
//
//                        } else {
//                            camera_view.startRecording(
//                                videoFile,
//                                cameraExecutor,
//                                object : OnVideoSavedCallback {
//                                    override fun onVideoSaved(outputFileResults: OutputFileResults) {
//
//                                        snack(outputFileResults.savedUri.toString())
//                                    }
//
//                                    override fun onError(
//                                        videoCaptureError: Int,
//                                        message: String,
//                                        cause: Throwable?
//                                    ) {
//                                        snack("保存错误")
//                                    }
//
//                                })
//                        }
//                    }
//                    setOnLongClickListener {
////                mediaCodec?.start()
//                        true
//                    }
//                }

//                checkAllMediaPms {
//                    if (!it.containsValue(false)) {
//                        Log.e("camera", "checkAllMediaPms")
//
//                    }
//                }

//            }
            verticalLayout {
                button("开始") {
                    setOnClickListener {
                        ffmpegVm.startFF()
                    }
                }

                button("停止") {
                    setOnClickListener {
                        ffmpegVm.stopFF()
                    }
                }

                button("转换") {
                    setOnClickListener {
                        ffmpegVm.vf()
                    }
                }
            }
        }
    }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ChoiceImageFragment().apply {
//            setOnSelectedBack {
//
//            }
//        }.show(parentFragmentManager, null)

        checkSdPms {
            if (!it.containsValue(false)) {
            }
        }


        Logger.e("onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ffmpegVm.stopFF()
    }


}