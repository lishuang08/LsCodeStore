/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.os.Build
import android.print.PrintDocumentAdapter
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

class WebContext {
    companion object {
        val debug = true
    }

    fun createPrintDocumentAdapter(documentName: String): PrintDocumentAdapter {
        validateWebView()
        return webView!!.createPrintDocumentAdapter(documentName)
    }

    fun goForward() {
        validateWebView()
        webView!!.goForward()
    }

    fun goBack() {
        validateWebView()
        webView!!.goBack()
    }

    fun canGoBack(): Boolean {
        validateWebView()
        return webView!!.canGoBack()
    }

    private fun validateWebView() {
        if (webView == null) {
            throw IllegalStateException("The WebView is not initialized yet.")
        }
    }

    internal var webView: WebView? = null
}

private fun WebView.setRef(ref: (WebView) -> Unit) {
    ref(this)
}

private fun WebView.setUrl(url: String) {
    if (originalUrl != url) {
        if (WebContext.debug) {
            Log.d("WebComponent", "WebComponent load url")
        }
        loadUrl(url)
    }
}

@Composable
fun WebComponent(
    url: String,
    webViewClient: WebViewClient = WebViewClient(),
    webContext: WebContext
) {
    if (WebContext.debug) {
        Log.d("WebComponent", "WebComponent compose " + url)
    }
    AndroidView(::WebView) {
        it.settings.run {

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
                it .setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }

//        blockNetworkImage = false//是否阻塞加载网络图片  协议http or https
//        allowFileAccess = false //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
            allowFileAccessFromFileURLs = true //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            allowUniversalAccessFromFileURLs =
                true//允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源

        }
        it.setRef { view -> webContext.webView = view }
        it.setUrl(url)
        it.webViewClient = webViewClient
    }
}