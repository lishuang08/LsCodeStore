package ls.yylx.lscodestore.basemodule.base

import android.app.Application

open class BaseAppliacation :Application(){

    override fun onCreate() {
        super.onCreate()
        appCtx = this

    }
    companion object{
        lateinit var appCtx: BaseAppliacation
    }
}