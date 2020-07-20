package ls.yylx.lscodestore.ui.splittiesview

import android.content.Context
import splitties.views.dsl.core.*

class MainFUi(override val ctx: Context) : Ui {
    val btn = button {
        text = "查看"
    }
    val btn2 = button {
        text = "CoordinatorLayout"
    }

    val btn3 = button {
        text = "motionlayout"
    }
    val btn4 = button {
        text = "customFragment"
    }

    override val root = verticalLayout {
        add(btn, lParams(matchParent, wrapContent) { })
        add(btn2, lParams(matchParent, wrapContent)  { })
        add(btn3, lParams(matchParent, wrapContent)  { })
        add(btn4, lParams(matchParent, wrapContent)  { })
    }
}