package ls.yylx.lscodestore.firstmodule.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.basemodule.GbifRoomViewModel
import ls.yylx.lscodestore.basemodule.rvadapter.paging.SpeciesPageAdapter
import ls.yylx.lscodestore.firstmodule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintSet
import org.jetbrains.anko.custom.customView

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

    val ankoC by lazy(LazyThreadSafetyMode.NONE) {
        ConstraintLayoutView()
    }

    lateinit var cl: ConstraintLayout
    lateinit var tb: TabLayout
    lateinit var vp2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adp = SpeciesPageAdapter().apply {

        }

        ankoC.setContentView(this)


        customView<ConstraintLayout> {
            tb = customView<TabLayout> {
                backgroundColorResource = R.color.colorPrimary
                setSelectedTabIndicatorColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                isTabIndicatorFullWidth = false
                tabMode = TabLayout.MODE_AUTO
                setTabTextColors(Color.parseColor("#9CCCF2"), Color.parseColor("#ffffff"))
//                        background = null
            }



            vp2 = customView<ViewPager2> {
                offscreenPageLimit = 6
            }
            constraintSet {
                connect(vp2.id, ConstraintSet.TOP, tb.id, ConstraintSet.BOTTOM)
            }

//                emptyTv = textView("没有内容") {
//                    visibility = View.GONE
//                    textColorResource = R.color.black
//                    textSize = 18f
//                    gravity = Gravity.CENTER
//                }.lparams(matchParent, matchParent)
        }


//        flowVm.list.observe(this, Observer {
//            launch {
//                adp.submitData(it)
//            }
//        })
    }


}

class ConstraintLayoutView : AnkoComponent<AnkoTestActivity> {
    lateinit var cl: ConstraintLayout
    lateinit var tb: TabLayout
    lateinit var vp2: ViewPager2
    lateinit var emptyTv: TextView
    override fun createView(ui: AnkoContext<AnkoTestActivity>) =
        with(ui) {
            cl = customView<ConstraintLayout> {
                tb = customView<TabLayout> {
                    backgroundColorResource = R.color.colorPrimary
                    setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                    isTabIndicatorFullWidth = false
                    tabMode = TabLayout.MODE_AUTO
                    setTabTextColors(Color.parseColor("#9CCCF2"), Color.parseColor("#ffffff"))
//                        background = null
                }


                vp2 = customView<ViewPager2> {
                    offscreenPageLimit = 6
                }

//                emptyTv = textView("没有内容") {
//                    visibility = View.GONE
//                    textColorResource = R.color.black
//                    textSize = 18f
//                    gravity = Gravity.CENTER
//                }.lparams(matchParent, matchParent)
            }
            cl
        }
}


class ViewPager2View : AnkoComponent<View> {
    lateinit var lnt: LinearLayout
    lateinit var tb: TabLayout
    lateinit var vp2: ViewPager2
    lateinit var emptyTv: TextView
    override fun createView(ui: AnkoContext<View>) =
        with(ui) {
            lnt = verticalLayout {
                tb = customView<TabLayout> {
                    backgroundColorResource = R.color.colorPrimary
                    setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                    isTabIndicatorFullWidth = false
                    tabMode = TabLayout.MODE_AUTO
                    setTabTextColors(Color.parseColor("#9CCCF2"), Color.parseColor("#ffffff"))
//                        background = null
                }

                vp2 = customView<ViewPager2> {
                    offscreenPageLimit = 6
                }.lparams(width = matchParent, height = matchParent)


                emptyTv = textView("没有内容") {
                    visibility = View.GONE
                    textColorResource = R.color.black
                    textSize = 18f
                    gravity = Gravity.CENTER
                }.lparams(matchParent, matchParent)
            }
            lnt
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