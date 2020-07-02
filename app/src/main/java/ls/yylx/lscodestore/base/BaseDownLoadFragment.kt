package ls.yylx.lscodestore.base

import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import java.io.File

open class BaseDownLoadFragment : BaseFragment() {
    var tvPb: TextView? = null
    var pb: ProgressBar? = null
    var md: MaterialDialog? = null

    var back: ((f : File ) -> Unit)? = null

    fun setOnFileDownloaded(item: (f : File ) -> Unit) {
        back = item
    }


}