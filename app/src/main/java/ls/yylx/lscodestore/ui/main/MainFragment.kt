package ls.yylx.lscodestore.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.ui.tooling.preview.Preview
import com.google.android.material.button.MaterialButton
import com.orhanobut.logger.Logger
import io.flutter.embedding.android.FlutterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.secondmodule.main.mainPage
import ls.yylx.lscodestore.tool.ChoiceImageData
import ls.yylx.lscodestore.tool.ChoiceImageSplittiesFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import splitties.views.onClick


class MainFragment : Fragment(), CoroutineScope by MainScope() {
    val isCompose = true

    val sparseArray = SparseArray<String>()

    val list by lazy(LazyThreadSafetyMode.NONE) {
        listOf("查看", "CoordinatorLayout", "flutter", "anko", "jetpack_compose", "xml", "hadrcode")
    }

    val mainVm by navGraphViewModels<MainViewModel>(R.navigation.main_navigation)

//    val mainVm by viewModels<MainViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = if (isCompose) cView else aView

    val aView by lazy(LazyThreadSafetyMode.NONE) {
        UI {
            verticalLayout {
                list.forEach { str ->
                    customView<MaterialButton> {
                        text = str
                        onClick {
                            when (str) {
                                "查看" -> {
                                    ChoiceImageSplittiesFragment().apply {
                                        setOnSelectedBack {
                                            Logger.e(it.toString())
                                        }
                                    }.show(parentFragmentManager, null)
                                }
                                "CoordinatorLayout" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_coordinatorLayoutFragment)
                                }
                                "flutter" -> {
                                    requireContext().startActivity(
                                        FlutterActivity.createDefaultIntent(requireActivity())
                                    )
                                }
                                "anko" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_ankoTestActivity)
                                }
                                "jetpack_compose" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_secondMainActivity)
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
                        }
                    }.lparams(matchParent, wrapContent) {
                        margin = dip(16)
                    }
                }
            }
        }.view
    }

    val cView
        get() = ComposeView(requireContext()).apply {
            setContent {
                composeView0()
            }
        }

    @Preview
    @Composable
    fun composeView0() {
        val (state, setState) = remember { mutableStateOf(0) }

        when (state) {
            0 -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    list.forEach {
                        Button(modifier = Modifier.padding(16.dp).fillMaxWidth(), onClick = {
                            when (it) {
                                "查看" -> {
                                    ChoiceImageSplittiesFragment().apply {
                                        setOnSelectedBack {
                                            Logger.e(it.toString())
                                        }
                                    }.show(parentFragmentManager, null)
                                }
                                "CoordinatorLayout" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_coordinatorLayoutFragment)
                                }
                                "flutter" -> {
                                    startActivity(
                                        FlutterActivity.createDefaultIntent(requireActivity())
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

                        }, content = { Text(text = it) })
                    }

                }
            }
            1 -> {
                mainPage()
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ChoiceImageData>("image_data")
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
        }
    }

}
