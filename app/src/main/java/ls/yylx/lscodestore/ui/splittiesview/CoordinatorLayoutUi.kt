package ls.yylx.lscodestore.ui.splittiesview

import android.content.Context
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import ls.yylx.lscodestore.R
import splitties.dimensions.dip
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.coordinatorlayout.appBarLParams
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.coordinatorlayout.defaultLParams
import splitties.views.dsl.core.*
import splitties.views.dsl.material.*
import splitties.views.gravityBottom

class CoordinatorLayoutUi(override val ctx: Context) : Ui {

    val mtl = toolbar {
        elevation = 0f
        setTitleTextColor(resources.getColor(R.color.white))
        title = "asdaaaaaaaab"
        background = null
    }

    val iv = imageView {
        setImageResource(R.drawable.ic_launcher_background)
        scaleType = ImageView.ScaleType.FIT_XY
    }

    val tableLayout = view(::TabLayout) {

    }

    val cardView = materialCardView {
        add(tableLayout, lParams(matchParent, dip(56)) { })
    }


    val ctl = collapsingToolbarLayout {
        add(iv, defaultLParams(matchParent, matchParent) {
            collapseMode = PARALLAX
            parallaxMultiplier = 0.4f
        })

        add(mtl, defaultLParams(matchParent, dip(56)) {
            expandedTitleMarginStart = dip(72)
            expandedTitleMarginBottom = dip(28)
            setExpandedTitleTextAppearance(R.style.TextAppearance_App_CollapsingToolbar_Expanded)
            setCollapsedTitleTextAppearance(R.style.TextAppearance_App_CollapsingToolbar_Collapsed)
            collapseMode = PIN

        })

        add(cardView, defaultLParams(matchParent, dip(56)) {
            gravity = gravityBottom
            marginStart = dip(24)
            marginEnd = dip(24)
            
        })
    }

    val appBar = appBarLayout {
        add(ctl, defaultLParams(matchParent, dip(200)) {
            minimumHeight = dip(56)
            scrollFlags = SCROLL or EXIT_UNTIL_COLLAPSED or SNAP
        })
    }


    val viewPager2 = view(::ViewPager2) {

    }


    val nev = view(::NestedScrollView) {
        add(viewPager2, lParams(matchParent, wrapContent) { })
    }

    override val root = coordinatorLayout {
        add(appBar, appBarLParams(wrapContent) {

        })

        add(nev, defaultLParams(matchParent, matchParent) {
            behavior = AppBarLayout.ScrollingViewBehavior()
        })

    }
}