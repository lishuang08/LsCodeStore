package ls.yylx.lscodestore.ui.ndk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ls.yylx.lscodestore.R

class NdkMainFragment : Fragment() {

    companion object {
        fun newInstance() = NdkMainFragment()
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
//            System.loadLibrary("oboe_code")

        }
    }
    external fun stringFromJNI(): String


//    external fun playAudioFromJNI(): String


    private lateinit var viewModel: NdkMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ndk_main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NdkMainViewModel::class.java)
        // TODO: Use the ViewModel
    }





}