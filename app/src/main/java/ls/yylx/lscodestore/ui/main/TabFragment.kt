package ls.yylx.lscodestore.ui.main

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.tool.ChoiceImageData
import ls.yylx.lscodestore.tool.ChoiceImageSplittiesFragment
import ls.yylx.lscodestore.ui.splittiesview.MainFUi


class TabFragment : Fragment(), CoroutineScope by MainScope() {


    val mainf by lazy(LazyThreadSafetyMode.NONE) {
        MainFUi(requireContext())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mainf.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}
