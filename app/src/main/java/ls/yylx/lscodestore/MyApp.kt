package ls.yylx.lscodestore


import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import androidx.room.Room
import com.jeremyliao.liveeventbus.LiveEventBus
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
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
//            DoraemonKit.install(this )
        }


        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        MMKV.initialize(this)
        MMKV.setLogLevel(if (BuildConfig.DEBUG) MMKVLogLevel.LevelDebug else MMKVLogLevel.LevelNone)

//        LiveEventBus.get().config()

//        appDb = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "aaaaa.db"
//        ).allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
//            .build()


//        CrashReport.initCrashReport(getApplicationContext(), "", true)
    }


    companion object {
        lateinit var instance: MyApp
        //        if (this::instance::isInitialized.get())
//        lateinit var boxStore: BoxStore


//        lateinit var appDb: AppDatabase
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

