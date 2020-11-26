package ls.yylx.lscodestore.base

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import org.jetbrains.anko.support.v4.toast

open class BaseFragment : Fragment(), CoroutineScope by MainScope() {
    /**默认添加registerForActivityResult方式，增加权限请求**/
    val pmsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it ->
            pmsCallBack?.invoke(it)
        }
    var pmsCallBack: ((MutableMap<String, Boolean>) -> Unit?)? = null


    val pmLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { it ->
            pmCallBack?.invoke(it)
        }
    var pmCallBack: ((Boolean) -> Unit?)? = null


    val singleToast by lazy {
        toast("")
    }


    var lastScrollY = 0

    var topViewOffset = 0
    var scrollPostion = 0

    var rv: RecyclerView? = null

    var nsv: NestedScrollView? = null

    var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (nsv == null) {
            rv?.run {
                (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    scrollPostion,
                    topViewOffset
                )
            }
        } else {
            nsv?.smoothScrollTo(0, lastScrollY)
        }
    }


    override fun onDestroyView() {
        if (nsv == null) {
            rv?.run {
                val lm = (layoutManager as LinearLayoutManager)
                topViewOffset = lm.getChildAt(0)?.top ?: 0
                scrollPostion = lm.findFirstVisibleItemPosition() ?: 0
            }
        } else {
            lastScrollY = nsv?.scrollY ?: 0
        }

        rv?.adapter = null
        rv = null

        super.onDestroyView()
        collapseSoftInputMethod()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()

    }
}
