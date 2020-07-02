package ls.yylx.lscodestore


import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import androidx.room.Room
import com.didichuxing.doraemonkit.DoraemonKit
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import com.tencent.smtt.sdk.QbSdk
import ls.yylx.lscodestore.db.RoomDb
import kotlin.properties.Delegates


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
        instance = this@MyApp
        val curProcess = Process.myUid()
        if (getProcessName(curProcess) != packageName) {
            return
        }

        initLibrary()
    }


    private fun initLibrary() {

        if (BuildConfig.DEBUG) {
            DoraemonKit.install(this)
        }


        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        MMKV.initialize(this)
        MMKV.setLogLevel(if (BuildConfig.DEBUG) MMKVLogLevel.LevelDebug else MMKVLogLevel.LevelNone)

//        LiveEventBus.get().config()


//        CrashReport.initCrashReport(getApplicationContext(), "", true)


        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {

                Logger.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {

            }
        })
    }


    companion object {
        lateinit var instance: MyApp


        val appDb by lazy {
            Room.databaseBuilder(
                instance,
                RoomDb::class.java, "lscodes.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        var cookie: String? = null

        var token: String? = null

        var WINDOW_WIDTH by Delegates.notNull<Int>()
        var WINDOW_HEIGHT by Delegates.notNull<Int>()

//        fun clearDb() {
//            boxStore.close()
//            boxStore.deleteAllFiles()
//            boxStore = MyObjectBox.builder().androidContext(MyApp.instance).build()
//        }


    }


    private fun getProcessName(uid: Int): String? {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val amrp = am.runningAppProcesses.find { it.uid == uid }
        return amrp?.processName

    }


}

