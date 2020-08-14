package ls.yylx.lscodestore.basemodule.network

import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.init.appCtx


abstract class SingleApiCallback<T> : Callback<T> {
    override fun onFailure(call: Call<T>?, t: Throwable?) {
        if (call!!.isCanceled) {
            onCancel()
            Logger.e("cancel")
        } else {
            onMyError(null ,t?.message?:"网络异常")
        }
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        if (response != null) {
            if (response.isSuccessful) {
                onMySuccess(response)
            } else {
                val code = response.code()

                val ctx = appCtx.applicationContext
                val id = ctx.resources.getIdentifier("code_$code", "string", ctx.packageName)
                val msg = ctx.getString(id)
                onMyError(response, msg)
            }
        }
    }

    open fun onMyError(response: Response<T>?, msg: String) {

    }

    open fun onMySuccess(response: Response<T>) {


    }

    open fun onCancel() {

    }


}