package ls.yylx.lscodestore.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Process
import android.util.AttributeSet
import android.view.View
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class X5WebView : WebView {
    private val client: WebViewClient = object : WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    constructor(arg0: Context?, arg1: AttributeSet?) : super(arg0, arg1) {
        this.webViewClient = client
        // this.setWebChromeClient(chromeClient);
        mySetting()
        this.view.isClickable = true
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        val ret = super.drawChild(canvas, child, drawingTime)
        canvas.save()
        val paint = Paint()
        paint.color = 0x7fff0000
        paint.textSize = 24f
        paint.isAntiAlias = true
        if (x5WebViewExtension != null) {
            canvas.drawText(
                this.context.packageName + "-pid:"
                        + Process.myPid(), 10f, 50f, paint
            )
            canvas.drawText(
                "X5  Core:" + QbSdk.getTbsVersion(this.context), 10f, 100f, paint
            )
        } else {
            canvas.drawText(
                this.context.packageName + "-pid:"
                        + Process.myPid(), 10f, 50f, paint
            )
            canvas.drawText("Sys Core", 10f, 100f, paint)
        }
        canvas.drawText(Build.MANUFACTURER, 10f, 150f, paint)
        canvas.drawText(Build.MODEL, 10f, 200f, paint)
        canvas.restore()
        return ret
    }

    constructor(arg0: Context?) : super(arg0) {
        setBackgroundColor(85621)
    }
}