package ls.yylx.lscodestore.firstmodule.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class VpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )  = UI {
        verticalLayout {
            textView {  text = "adad" }
        }
    }.view


    companion object {
        fun newInstance() = VpFragment().apply {

        }
    }

}