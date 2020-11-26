package ls.yylx.lscodestore.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzer
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.orhanobut.logger.Logger
import io.flutter.embedding.android.FlutterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.basemodule.checkArrayPermissions
import ls.yylx.lscodestore.firstmodule.ChoiceImageData
import ls.yylx.lscodestore.secondmodule.main.mainPage
import ls.yylx.lscodestore.secondmodule.theme.JetpackTheme
import ls.yylx.lsmediaeditor.CameraViewFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.include
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.verticalLayout


class MainFragment : Fragment(), CoroutineScope by MainScope() {
    val mainState = mutableStateOf(0)

    val isCompose = true

    val sparseArray = SparseArray<String>()

    val list by lazy(LazyThreadSafetyMode.NONE) {
        listOf("hms-scan", "ffmpeg", "camerax_view", "flutter", "anko", "jetpack_compose", "xml", "hadrcode")
    }

    val mainVm by viewModels <MainViewModel>( )

//    val mainVm by viewModels<MainViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =   cView

//    val aView by lazy(LazyThreadSafetyMode.NONE) {
//        UI {
//            verticalLayout {
//                list.forEach { str ->
//                    customView<MaterialButton> {
//                        text = str
//                        onClick {
//                            when (str) {
//                                "查看" -> {
//
//
//                                }
//                                "CoordinatorLayout" -> {
//                                    findNavController().navigate(R.id.action_mainFragment_to_coordinatorLayoutFragment)
//                                }
//                                "flutter" -> {
//                                    requireContext().startActivity(
//                                        FlutterActivity.createDefaultIntent(requireActivity())
//                                    )
//                                }
//                                "anko" -> {
//                                    findNavController().navigate(R.id.action_mainFragment_to_ankoTestActivity)
//                                }
//                                "jetpack_compose" -> {
//                                }
//                                "xml" -> {
//                                    findNavController().navigate(R.id.action_mainFragment_to_xmlLntActivity)
//                                }
//                                //hardcode （硬编码）方式跳转module
//                                "hadrcode" -> {
//                                    try {
//                                        val intent = Intent()
//                                        intent.setClassName(
//                                            requireContext(),
//                                            "ls.yylx.lscodestore.firstmodule.ui.main.AnkoTestActivity"
//                                        )
//                                        startActivity(intent)
//                                    } catch (e: Exception) {
//                                        toast("没有跳转目标")
//                                        Logger.e(e.message ?: "")
//                                    }
//                                }
//                            }
//                        }
//                    }.lparams(matchParent, wrapContent) {
//                        margin = dip(16)
//                    }
//                }
//            }
//        }.view
//    }

    val cView
        get() = ComposeView(requireContext()).apply {
            setContent {
                composeView0()
            }
        }

    @Preview
    @Composable
    fun composeView0() {
        val (state, setState) = remember { mainState }
        JetpackTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
//                                if (state is State_Home) {
//
//                                } else {
//                                    setState.invoke(State_Home(hashMapOf()))
//                                }
                            }) {
                                val image =
                                    vectorResource(ls.yylx.lscodestore.secondmodule.R.drawable.ic_back)
                                Image(image)
                            }
                        },
                        title = {
//                            Text(text = state.name)
                        }
                    )
                }
            ) {
                when (state) {
                    0 -> {
                        ScrollableColumn(modifier = Modifier.padding(16.dp)) {
                            list.forEach {
                                Button(
                                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                    onClick = {
                                        when (it) {
                                            "hms-scan" -> {
                                                checkArrayPermissions(arrayOf(Manifest.permission.CAMERA)) {
                                                    if (it) {

                                                        //“QRCODE_SCAN_TYPE”和“DATAMATRIX_SCAN_TYPE表示只扫描QR和Data Matrix的码
                                                        val options =
                                                            HmsScanAnalyzerOptions.Creator()
                                                                .setHmsScanTypes(
                                                                    HmsScan.QRCODE_SCAN_TYPE,
                                                                    HmsScan.DATAMATRIX_SCAN_TYPE
                                                                ).create()

                                                        val barcodeDetector =
                                                            HmsScanAnalyzer(options)
//                                                        val image = MLFrame.fromBitmap(bitmap)

//                                                        val result =
//                                                            barcodeDetector.analyseFrame(image)
//展示扫码结果
//展示扫码结果

                                                        ScanUtil.startScan(
                                                            requireActivity(),
                                                            100111,
                                                            options
                                                        )
                                                    } else {
                                                        toast("没有相机权限，无法拍照")
                                                    }
                                                }

                                            }

                                            "camerax_view" ->{
                                                CameraViewFragment().show(childFragmentManager,null )

                                            }
                                            "ffmpeg" -> {
//                                                CameraRecordFragment().show(childFragmentManager,null )
                                                findNavController().navigate(R.id.action_mainFragment_to_ffmpegFragment)
                                            }
                                            "flutter" -> {
                                                startActivity(
                                                    FlutterActivity.createDefaultIntent(
                                                        requireActivity()
                                                    )
                                                )
                                            }
                                            "anko" -> {
                                                findNavController().navigate(R.id.action_mainFragment_to_ankoTestActivity)
                                            }
                                            "jetpack_compose" -> {
                                                setState.invoke(1)
                                            }
                                            "xml" -> {
                                                findNavController().navigate(R.id.action_mainFragment_to_xmlLntActivity)
                                            }
                                            //hardcode （硬编码）方式跳转module
                                            "hadrcode" -> {
                                                try {
                                                    val intent = Intent()
                                                    intent.setClassName(
                                                        requireContext(),
                                                        "ls.yylx.lscodestore.firstmodule.ui.main.AnkoTestActivity"
                                                    )
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    toast("没有跳转目标")
                                                    Logger.e(e.message ?: "")
                                                }
                                            }
                                        }

                                    },
                                    content = { Text(text = it) })
                            }
                            AndroidView(viewBlock ={
                               UI{
                                   verticalLayout {
                                       include<ConstraintLayout>(R.layout.test_shape).run {
                                           find<AppCompatImageView>(R.id.aiv_test).clipToOutline = true
                                       }
                                   }


                               }.view
                            }, modifier = Modifier.padding(16.dp) )
                        }
                    }
                    1 -> {
                        mainPage()
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ChoiceImageData>(
            "image_data"
        )
            ?.observe(viewLifecycleOwner, Observer {
                it?.run {

                }
            })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,  // LifecycleOwner
            backCallBack
        )

    }

    val backCallBack = object : OnBackPressedCallback(
        true // default to enabled
    ) {
        override fun handleOnBackPressed() {
            val canBack = mainState.value != 0
            if (canBack) {
                mainState.value = 0
            } else {
                false
            }
        }
    }

      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if (requestCode == 100111) {
            val obj: HmsScan = data.getParcelableExtra(ScanUtil.RESULT)!!
            obj?.let {

                Logger.e(it.toString())

            }
        }
    }


}


