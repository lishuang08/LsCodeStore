package ls.yylx.lscodestore.ui.testJump

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ls.yylx.lscodestore.R
import org.jetbrains.anko.button
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.verticalLayout

class Fragment0 : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = UI {
        verticalLayout {
            button {
                text = "btn0"
                setOnClickListener {
                    findNavController().navigate(R.id.action_fragment0_to_fragment1)
                }
            }
        }
    }.view
}