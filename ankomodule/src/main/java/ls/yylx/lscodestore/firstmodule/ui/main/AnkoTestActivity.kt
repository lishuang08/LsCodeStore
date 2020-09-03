package ls.yylx.lscodestore.firstmodule.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.basemodule.GbifRoomViewModel
import ls.yylx.lscodestore.firstmodule.R
import ls.yylx.lscodestore.firstmodule.viewPager2
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout


class AnkoTestActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    val flowVm by viewModels<GbifRoomViewModel>()

    val vpMain by lazy(LazyThreadSafetyMode.NONE) {
        ViewPager2View()
    }
    val pages = listOf(
        "page0",
        "page1",
        "page2",
        "page3",
        "page4",
        "page5",
        "page6",
        "page7",
        "page8",
        "page9"
    )
    var pageSelected = 0

    override fun onDestroy() {
        super.onDestroy()
        pageSelected = vpMain.vp2.currentItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vpMain.setContentView(this)

        val adpf = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount() = pages.size


            override fun createFragment(position: Int): Fragment {
                val item = pages[position]

                return when (item) {

                    else -> {

                        VpFragment.newInstance().apply {
                            arguments =
                                androidx.core.os.bundleOf(
                                    "title" to item
                                )
                        }
                    }
                }
            }
        }
        vpMain.run {
            vp2.run {
                adapter = adpf
                setCurrentItem(pageSelected, false)
            }

            TabLayoutMediator(tb, vp2) { tab, position ->
                tab.view.tab
                tab.text = pages[position]
            }
        }


//        flowVm.list.observe(this, Observer {
//            launch {
//                adp.submitData(it)
//            }
//        })
    }

}


class ViewPager2View : AnkoComponent<AppCompatActivity> {
    lateinit var lnt: LinearLayout
    lateinit var tb: TabLayout
    lateinit var vp2: ViewPager2
    lateinit var emptyTv: TextView
    override fun createView(ui: AnkoContext<AppCompatActivity>) =
        with(ui) {
            lnt = verticalLayout {
                tb = tabLayout {
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

                vp2 = viewPager2 {
                    offscreenPageLimit = 6
                    isSaveEnabled = true
                    requestDisallowInterceptTouchEvent(true)
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

