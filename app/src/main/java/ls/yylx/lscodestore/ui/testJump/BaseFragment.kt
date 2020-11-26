package ls.yylx.lscodestore.ui.testJump

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/***基础fragment,默认开启协程在主线程
 * 提供一个默认job，可以赋值，页面onDestroy时候，取消
 * EventBus主要是旧代码需要，后续会逐步去掉
 *
 * */
open class BaseFragment : Fragment(), CoroutineScope by MainScope() {
    var openLifeLogger = true


    override fun onAttach(context: Context) {
        super.onAttach(context)
//        lifecycle.addObserver(MyLifecleObserver(javaClass.simpleName))
       if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onStart")
    }
    override fun onResume() {
        super.onResume()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onResume")
    }
    override fun onPause() {
        super.onPause()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onPause")
    }

    override fun onStop() {
        super.onStop()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        if (openLifeLogger) Log.e("life","${javaClass.simpleName}   onDetach")
    }


}
