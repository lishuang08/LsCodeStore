package ls.yylx.lscodestore.mlkit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.view.CameraView
import androidx.fragment.app.Fragment
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.support.v4.UI


class MlKitFragment : Fragment() {
//    val videoCaptureConfig = VideoCaptureConfig(OptionsBundle()).apply {
//        setLensFacing(lensFacing)
//        setTargetAspectRatio(screenAspectRatio)
//        setTargetRotation(viewFinder.display.rotation)
//
//    }.build()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI {
        customView<CameraView> {

        }


    }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        videoCapture = VideoCapture(videoCaptureConfig)
//
//        CameraX.bindToLifecycle(this, preview, imageCapture, videoCapture)
    }



}