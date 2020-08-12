package ls.yylx.lscodestore.firstmodule.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.basemodule.GbifRoomViewModel
import ls.yylx.lscodestore.basemodule.rvadapter.paging.SpeciesPageAdapter
import org.jetbrains.anko.recyclerview.v7.recyclerView

//@OptIn(ExperimentalPagingApi::class)
//class GithubRemoteMediator(
//    private val query: String,
//    private val service: GithubService,
//    private val repoDatabase: RepoDatabase
//) : RemoteMediator<Int, Repo>() {
//

//    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): RemoteMediator.MediatorResult {
//        TODO("Not yet implemented")
//    }
//}


class AnkoTestActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    val flowVm by viewModels<GbifRoomViewModel>()
    lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adp = SpeciesPageAdapter().apply {

        }
        recyclerView {
            layoutManager = LinearLayoutManager(context)
            adapter = adp
        }

//
//        flowVm.list.observe(this, Observer {
//            launch {
//                adp.submitData(it)
//            }
//        })
    }


}

fun ViewStub.deflate(view: View): ViewStub {
    val viewParent = view.parent

    if (viewParent != null && viewParent is ViewGroup) {
        val index = viewParent.indexOfChild(view)
        viewParent.removeView(view)
        val viewStub = ViewStub(context).apply {
            inflatedId = this@deflate.inflatedId
            layoutParams = this@deflate.layoutParams
        }
        viewParent.addView(viewStub, index)
        return viewStub
    } else {
        throw IllegalStateException("Inflated View has not a parent")
    }
}