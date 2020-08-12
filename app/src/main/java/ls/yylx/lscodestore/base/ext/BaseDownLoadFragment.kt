package ls.yylx.lscodestore.base.ext

import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.orhanobut.logger.Logger
import com.tencent.smtt.sdk.QbSdk
import ls.yylx.lscodestore.base.BaseFragment
import ls.yylx.lscodestore.basemodule.PreviewItem
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.*

open class BaseDownLoadFragment : BaseFragment() {
    var tvPb: TextView? = null
    var pb: ProgressBar? = null
    var md: MaterialDialog? = null

    open fun BaseDownLoadFragment.downloaded(file: File, item: PreviewItem) {
        openMyFileReader(requireContext(), file.path)
    }

    private fun openMyFileReader(context: Context, pathName: String?) {
        val params =
            HashMap<String, String>()
        params["local"] = "true"
        val Object = JSONObject()
        try {
            Object.put("pkgName", context.applicationContext.packageName)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        params["menuData"] = Object.toString()
        QbSdk.getMiniQBVersion(context)
        val ret = QbSdk.openFileReader(
            context, pathName, params
        ) { p0 -> Logger.e(p0.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvPb =null
        pb = null
        md = null
    }
}