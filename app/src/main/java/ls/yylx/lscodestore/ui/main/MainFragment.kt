package ls.yylx.lscodestore.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.jetbrains.anko.button
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.UI

class MainFragment : Fragment() {

    val sparseArray = SparseArray<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return UI {
            frameLayout {
                button("跳转") {
                    setOnClickListener {
                        val uri = Uri.parse("yylx://ls.yylx.firstmodul")
                        findNavController().navigate(uri)
                    }

                }
            }
        }.view
    }


}
