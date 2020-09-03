package ls.yylx.lscodestore.basemodule

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.just.agentweb.AbsAgentWebSettings
import com.just.agentweb.AgentWeb
import com.just.agentweb.IAgentWebSettings



class MyWebSetting : AbsAgentWebSettings() {
    var agentWeb: AgentWeb? = null

    override fun bindAgentWebSupport(agentWeb: AgentWeb) {
        this.agentWeb = agentWeb
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun toSetting(webView: WebView): IAgentWebSettings<*>? {
        super.toSetting(webView)
        
        
        webSettings.run {
            builtInZoomControls = true
            useWideViewPort = true
            loadWithOverviewMode = true
            savePassword = true
            saveFormData = true
            javaScriptEnabled = true     // enable navigator.geolocation
            setGeolocationEnabled(true)
            domStorageEnabled = true


            layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
            loadWithOverviewMode = true
            defaultTextEncodingName = "utf-8"
            cacheMode = WebSettings.LOAD_DEFAULT
            setAppCacheEnabled(true)
            setSupportZoom(true)
            javaScriptEnabled = true

            savePassword = false

//        domStorageEnabled = true;//开启DOM storage API功能

            if (Build.VERSION.SDK_INT >= 21) {
                //适配5.0不允许http和https混合使用情况
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }

//        blockNetworkImage = false//是否阻塞加载网络图片  协议http or https
//        allowFileAccess = false //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
            allowFileAccessFromFileURLs = true //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            allowUniversalAccessFromFileURLs =
                true//允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源

        }


        return this
    }


}