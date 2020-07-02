package ls.yylx.lscodestore.webview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.net.toUri
import com.orhanobut.logger.Logger
import com.tencent.smtt.sdk.WebViewClient
import ls.yylx.lscodestore.base.BaseDownLoadFragment
import ls.yylx.lscodestore.base.openDownLoadFile
import ls.yylx.lscodestore.dataclass.PreviewItem
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.add
import splitties.views.dsl.core.frameLayout


class X5WebViewFragment : BaseDownLoadFragment() {
    class x5Ui(override val ctx: Context) : Ui {

        val fl = frameLayout {

        }
        override val root: View = fl

    }

    val mUi by lazy(LazyThreadSafetyMode.NONE) {
        x5Ui(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mUi.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mWebView =
            X5WebView(requireContext(), null)

        mUi.fl.addView(
            mWebView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        val url =
            "http://xinjiang.gov.cn/xinjiang/fgwjx/202004/3ff5afd0742e48cc97cf0e23894c3ac5/files/cce9ed06982e4753928076a6b4c0f52d.pdf"
        mWebView.loadUrl(url)


        mWebView.setWebViewClient(object : WebViewClient() {

        })
        mWebView.setDownloadListener { arg0, arg1, arg2, arg3, arg4 ->
            setOnFileDownloaded {
                Logger.e(it.toUri().toString())
                mWebView.loadUrl(it.toUri().toString())
            }
            openDownLoadFile(
                PreviewItem(
                    false,
                    url,
                    "文件",
                    "pdf"
                )
            )

        }

    }


}