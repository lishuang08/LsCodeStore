package ls.yylx.lscodestore.mlkit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.textView


class MlKitFragment : Fragment() {
    val handler = Handler(Looper.getMainLooper()) {

        true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI {

        textView("mlkit-face")

    }.view



}