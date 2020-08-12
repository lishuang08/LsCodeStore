package ls.yylx.lscodestore.basemodule.network

import com.google.gson.GsonBuilder
import ls.yylx.lscodestore.basemodule.R
import ls.yylx.lscodestore.basemodule.network.InitOkhttp.addDownloadInterceptor
import ls.yylx.lscodestore.basemodule.network.InitOkhttp.addInterceptor
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import splitties.init.appCtx
import java.net.Proxy
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object SingleRetrofit {
    val BaseUrlGbif = "http://api.gbif.org/v1/"

    val retrofitGbif by lazy {
        Retrofit.Builder()
            .client(getOkhttpBuilder())
            .baseUrl(
                BaseUrlGbif)
            .addConverterFactory( GsonConverterFactory.create(
                GsonBuilder().apply {
                    setPrettyPrinting()
                }.create()
            ))
            .build().create(GbifService::class.java)
    }


    val retrofitApi by lazy {
        Retrofit.Builder()
            .client(getOkhttpBuilder())
            .baseUrl(
                appCtx.getString(
                    R.string.base_url
                ))
            .addConverterFactory( GsonConverterFactory.create(
                GsonBuilder().apply {
                    setPrettyPrinting()
                }.create()
            ))
            .build().create(ApiResponse::class.java)
    }

    fun getOkhttpBuilder(): OkHttpClient {
        // 创建OkHttpClient
        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(null)
            .proxy(Proxy.NO_PROXY)
            .sslSocketFactory(createSSLSocketFactory()!!, mMyTrustManager!!)
            .hostnameVerifier(TrustAllHostnameVerifier())

            .retryOnConnectionFailure(false)  // 错误重连
            // 支持HTTPS
            .connectionSpecs(
                listOf(
                    ConnectionSpec.CLEARTEXT,
                    ConnectionSpec.MODERN_TLS
                )
            ) //明文Http与比较新的Https

        // cookie管理
        // .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this)))

        // 添加各种插入器
        addInterceptor(builder)


        return builder.build()
    }


    fun getDownloadBuilder(downInterceptor: Interceptor): OkHttpClient {
        // 创建OkHttpClient
        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(null)
            .sslSocketFactory(createSSLSocketFactory()!!, mMyTrustManager!!)
            .hostnameVerifier(TrustAllHostnameVerifier())

            .retryOnConnectionFailure(false)  // 错误重连
            // 支持HTTPS
            .connectionSpecs(
                listOf(
                    ConnectionSpec.CLEARTEXT,
                    ConnectionSpec.MODERN_TLS
                )
            ) //明文Http与比较新的Https

        // cookie管理
        // .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this)))

        // 添加各种插入器
        addDownloadInterceptor(builder, downInterceptor)

        return builder.build()
    }


    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null
        try {
            mMyTrustManager =
                MyTrustManager()
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(mMyTrustManager!!), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return ssfFactory
    }


    //实现HostnameVerifier接口
    private class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return ( hostname == "120.197.144.196" || hostname == "10.146.0.204")
        }
    }

    private var mMyTrustManager: MyTrustManager? = null

    //实现X509TrustManager接口
    private class MyTrustManager : X509TrustManager {
        override fun getAcceptedIssuers() = arrayOfNulls<X509Certificate>(0)


        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }
    }


//    //Glide设置cookie用
//    var LazyHeadersbuilder = LazyHeaders.Builder()


//
//    fun getGlideUrl(url: String): GlideUrl {
//        LazyHeadersbuilder.setHeader("Cookie", Token)
//        return GlideUrl(url, LazyHeadersbuilder.build())
//    }


}