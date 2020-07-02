package ls.yylx.lscodestore.ui.splittiesview

import android.content.Context
import android.os.Build
import android.view.Gravity
import ls.yylx.lscodestore.R
import splitties.dimensions.dip
import splitties.resources.styledDrawable
import splitties.views.dsl.core.*
import splitties.views.dsl.recyclerview.recyclerView

class RvUi(override val ctx: Context) : Ui {
    val v = verticalLayout {
        val tv = textView {
            text = "aaa"
            gravity = Gravity.CENTER_VERTICAL
            if (Build.VERSION.SDK_INT >= 23) {
                foreground = styledDrawable(R.attr.selectableItemBackgroundBorderless)
            }
            setPadding( dip(16), 0, 0, 0)
        }
        add(tv, lParams(matchParent,dip( 40)) { })
    }

    override val root = recyclerView {

    }
}