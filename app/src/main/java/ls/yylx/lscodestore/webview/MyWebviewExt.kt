package ls.yylx.lscodestore.webview

import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView

fun WebView.mySetting() {
    settings.run {
        javaScriptEnabled = true
        javaScriptCanOpenWindowsAutomatically = true
        allowFileAccess = true
        layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        setSupportZoom(true)
        builtInZoomControls = true
        useWideViewPort = true
        setSupportMultipleWindows(true)
        // setLoadWithOverviewMode(true);
        // setLoadWithOverviewMode(true);
        setAppCacheEnabled(true)
        // setDatabaseEnabled(true);
        // setDatabaseEnabled(true);
        domStorageEnabled = true
        setGeolocationEnabled(true)
        setAppCacheMaxSize(Long.MAX_VALUE)
        // setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        // setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        pluginState = WebSettings.PluginState.ON_DEMAND
        // setRenderPriority(WebSettings.RenderPriority.HIGH);
        // setRenderPriority(WebSettings.RenderPriority.HIGH);
        cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

//		setAppCachePath(this.getDir("appcache", 0).getPath());
//		setDatabasePath(this.getDir("databases", 0).getPath());
//		setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
        // setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);

//		setAppCachePath(this.getDir("appcache", 0).getPath());
//		setDatabasePath(this.getDir("databases", 0).getPath());
//		setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
        // setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        pluginState = WebSettings.PluginState.ON_DEMAND

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }
}