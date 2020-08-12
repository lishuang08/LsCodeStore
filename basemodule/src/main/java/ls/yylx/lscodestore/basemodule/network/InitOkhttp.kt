package ls.yylx.lscodestore.basemodule.network

import android.text.TextUtils
import ls.yylx.lscodestore.basemodule.BuildConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Created by ls on 2017/10/23.
 */

object InitOkhttp {
    fun addInterceptor(builder: OkHttpClient.Builder) {
        // 添加Header
        builder.addInterceptor(HttpHeaderBookInterceptor())
//        // 添加缓存控制策略
//        val cacheDir = MyApp.instance.externalCacheDir ?: MyApp.instance.cacheDir
//        val cache = Cache(cacheDir, 1024 * 1024 * 100)
//        builder.cache(cache).addInterceptor(cacheInterceptor)


        if (BuildConfig.DEBUG) {
            // 添加http log
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logger)
        }
    }


    fun addDownloadInterceptor(
        builder: OkHttpClient.Builder,
        downInterceptor: Interceptor
    ) {
        // 添加Header
        builder.addInterceptor(HttpHeaderBookInterceptor())
//        // 添加缓存控制策略
//        val cacheDir = MyApp.instance.externalCacheDir ?: MyApp.instance.cacheDir
//        val cache = Cache(cacheDir, 1024 * 1024 * 100)
//        builder.cache(cache).addInterceptor(cacheInterceptor)
        builder.addNetworkInterceptor(downInterceptor)

        if (BuildConfig.DEBUG) {
            // 添加http log
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logger)
        }
    }

    internal class HttpHeaderBookInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val cacheControl = CacheControl.Builder()
                .noCache()
                .build()

            val request = chain.request()
                .newBuilder().apply {
                    cacheControl(cacheControl)

//                    if (!MyApp.token.isNullOrEmpty()) addHeader("token", MyApp.token
//                        ?: "")
                }
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Accept", "*/*")
//                .addHeader("Cookie", "add cookies here")


                .build()
            return chain.proceed(request)

        }
    }


    val cacheInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)

            var cacheControl = request.cacheControl.toString()
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60"
            }
            return response.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build()
        }
    }
}
