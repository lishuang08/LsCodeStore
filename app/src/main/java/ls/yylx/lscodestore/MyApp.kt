package ls.yylx.lscodestore


import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import com.didichuxing.doraemonkit.DoraemonKit
import com.google.gson.GsonBuilder
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import com.tencent.smtt.sdk.QbSdk
import dagger.hilt.android.HiltAndroidApp
import ls.yylx.lscodestore.basemodule.R
import ls.yylx.lscodestore.db.RoomDb
import ls.yylx.lscodestore.network.ApiGbif
import ls.yylx.lscodestore.network.SingleRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

@HiltAndroidApp
class MyApp : Application() {
    init { //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white) //全局设置主题颜色
            MaterialHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
//            //指定为经典Footer，默认是 BallPulseFooter
////            MaterialHeader(context).setDrawableSize(20f)
//        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val curProcess = Process.myUid()
        if (getProcessName(curProcess) != packageName) {
            return
        }

        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }

        initLibrary()
    }

    val appModule = module {
        single {
            Retrofit.Builder()
                .client(SingleRetrofit.getOkhttpBuilder())
                .baseUrl(
                    MyApp.instance.getString(
                        ls.yylx.lscodestore.R.string.base_url
                    )
                )
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().apply {
                            setPrettyPrinting()
                        }.create()
                    )
                )
                .build().create(ApiGbif::class.java)
        }
    }

    private fun initLibrary() {

        instance = this

        if (BuildConfig.DEBUG) {
            DoraemonKit.install(this)
        }
        appDb = RoomDb.get(this)

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return ls.yylx.lscodestore.basemodule.BuildConfig.DEBUG
            }
        })

        MMKV.initialize(this)
        MMKV.setLogLevel(if (ls.yylx.lscodestore.basemodule.BuildConfig.DEBUG) MMKVLogLevel.LevelDebug else MMKVLogLevel.LevelNone)

//        LiveEventBus.get().config()

//        CrashReport.initCrashReport(getApplicationContext(), "", true)


        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {

                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {

            }
        })
    }


    private fun getProcessName(uid: Int): String? {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val amrp = am.runningAppProcesses.find { it.uid == uid }
        return amrp?.processName

    }

    companion object {
        lateinit var instance: MyApp

        lateinit var appDb: RoomDb

        var cookie: String? = null

        var token: String? = null

        var WINDOW_WIDTH by Delegates.notNull<Int>()
        var WINDOW_HEIGHT by Delegates.notNull<Int>()


    }

}

