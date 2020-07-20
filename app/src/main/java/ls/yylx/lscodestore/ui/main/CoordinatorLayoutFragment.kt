package ls.yylx.lscodestore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.ui.splittiesview.CoordinatorLayoutUi


class CoordinatorLayoutFragment : Fragment(), CoroutineScope by MainScope() {

    val list by lazy {
        listOf(
            "AAAAA",
            "BBBBB",
            "CCCCC"
        )
    }


    val clUi by lazy(LazyThreadSafetyMode.NONE) {
        CoordinatorLayoutUi(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = clUi.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clUi.run {
            viewPager2.run {
                adapter = object : FragmentStateAdapter(this@CoordinatorLayoutFragment) {
                    override fun getItemCount() = list.size

                    override fun createFragment(position: Int) = TabFragment()
                }
            }
            TabLayoutMediator(tableLayout, viewPager2) { tab, position ->
//            tab.view.tab?.customView = UI {
//                textView {
//                    text = list[position].key
//                    gravity = Gravity.CENTER
//
//                }
//            }.view
                tab.text = list[position]
            }.attach()


            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                Logger.e("verticalOffset    $verticalOffset")


            })
        }

    }


}
