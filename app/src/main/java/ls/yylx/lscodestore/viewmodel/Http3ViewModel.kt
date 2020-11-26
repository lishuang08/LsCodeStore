package ls.yylx.lscodestore.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ls.yylx.lscodestore.MyApp
import org.chromium.net.CronetEngine
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Http3ViewModel(app:Application) :AndroidViewModel(app ){
    val appCtx by lazy {
        getApplication<MyApp>()
    }
    val executor: Executor = Executors.newSingleThreadExecutor()


    fun http3(){
        val myBuilder = CronetEngine.Builder(appCtx)
        val cronetEngine: CronetEngine = myBuilder.build()

        val requestBuilder = cronetEngine.newUrlRequestBuilder(
            "https://www.baidu.com",
            object : UrlRequest.Callback(){
                override fun onRedirectReceived(
                    request: UrlRequest?,
                    info: UrlResponseInfo?,
                    newLocationUrl: String?
                ) {
                    Log.e("http3-重定向", info.toString())
                }

                override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
                    Log.e("http3-返回开始", info.toString())
                }

                override fun onReadCompleted(
                    request: UrlRequest?,
                    info: UrlResponseInfo?,
                    byteBuffer: ByteBuffer?
                ) {
                    Log.e("http3-完成", info.toString())

                }

                override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
                    Log.e("http3-成功", info.toString())
                }

                override fun onFailed(
                    request: UrlRequest?,
                    info: UrlResponseInfo?,
                    error: CronetException?
                ) {
                    Log.e("http3-失败", info.toString())
                }

            },
            executor
        )

        val request: UrlRequest = requestBuilder.build()

        request.start()
    }
}