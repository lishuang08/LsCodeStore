package ls.yylx.lscodestore


import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import com.didichuxing.doraemonkit.DoraemonKit
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import com.tencent.smtt.sdk.QbSdk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ls.yylx.lscodestore.basemodule.R
import splitties.init.appCtx
import kotlin.properties.Delegates

//@HiltAndroidApp
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
        val curProcess = Process.myUid()
        if (getProcessName(curProcess) != packageName) {
            return
        }
        instance = this

        initLibrary()
    }


    private fun initLibrary() {
        GlobalScope.launch {
            if (BuildConfig.DEBUG) {
                DoraemonKit.install(instance)
            }

            Logger.addLogAdapter(object : AndroidLogAdapter() {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            })

            MMKV.initialize(instance)
            MMKV.setLogLevel(if (ls.yylx.lscodestore.basemodule.BuildConfig.DEBUG) MMKVLogLevel.LevelDebug else MMKVLogLevel.LevelNone)

//        LiveEventBus.get().config()

//        CrashReport.initCrashReport(getApplicationContext(), "", true)


            //x5内核初始化接口
            QbSdk.initX5Environment(appCtx, object : QbSdk.PreInitCallback {
                override fun onViewInitFinished(arg0: Boolean) {

                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    Logger.d("app", " onViewInitFinished is $arg0")
                }

                override fun onCoreInitFinished() {

                }
            })
        }
    }


    private fun getProcessName(uid: Int): String? {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val amrp = am.runningAppProcesses.find { it.uid == uid }
        return amrp?.processName

    }

    companion object {
        lateinit var instance: MyApp


        var cookie: String? = null

        var token: String? = null

        var WINDOW_WIDTH by Delegates.notNull<Int>()
        var WINDOW_HEIGHT by Delegates.notNull<Int>()


    }

}

