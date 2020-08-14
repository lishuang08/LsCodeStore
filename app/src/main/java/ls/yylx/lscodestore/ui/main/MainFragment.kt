package ls.yylx.lscodestore.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.orhanobut.logger.Logger
import io.flutter.embedding.android.FlutterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.tool.ChoiceImageData
import ls.yylx.lscodestore.tool.ChoiceImageSplittiesFragment
import ls.yylx.lscodestore.ui.splittiesview.MainFUi
import splitties.toast.toast


class MainFragment : Fragment(), CoroutineScope by MainScope() {

    val sparseArray = SparseArray<String>()

    val mainf by lazy(LazyThreadSafetyMode.NONE) {
        MainFUi(requireContext())
    }


    val mainVm by navGraphViewModels<MainViewModel>(R.navigation.main_navigation)


//    val mainVm by viewModels<MainViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mainf.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainf.run {
            btn.setOnClickListener {
                ChoiceImageSplittiesFragment().apply {
                    setOnSelectedBack {
                        Logger.e(it.toString())
                    }
                }.show(parentFragmentManager, null)
            }
            btn2.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_coordinatorLayoutFragment)
            }
            btn3.setOnClickListener {
                startActivity(
                    FlutterActivity.createDefaultIntent(requireActivity())
                )
            }
            btn4.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_ankoTestActivity)
            }
            btn5.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_secondMainActivity)
            }
            btn6.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_xmlLntActivity)
            }
            //hardcode （硬编码）方式跳转module
            btn7.setOnClickListener {
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




        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ChoiceImageData>("image_data")
            ?.observe(viewLifecycleOwner, Observer {
                it?.run {

                }
            })
    }


}
