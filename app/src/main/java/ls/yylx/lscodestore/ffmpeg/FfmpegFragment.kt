package ls.yylx.lscodestore.ffmpeg


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.base.BaseFragment
import ls.yylx.lscodestore.ext.checkSdPms
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.textView

class FfmpegFragment : BaseFragment() {
    val ffmpegVm by viewModels<FfmpegViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI{
        frameLayout {
            textView {
                text ="ff"
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
            if (!it.containsValue(false )){
                ffmpegVm.ffmpeg()
            }
        }


        Logger.e("onViewCreated")
    }



}