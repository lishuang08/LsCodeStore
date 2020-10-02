package ls.yylx.lscodestore.qrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huawei.hms.scankit.ViewfinderView
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.support.v4.UI

class CameraxFragment :Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI{
        customView<ViewfinderView> {  }

    }.view


}