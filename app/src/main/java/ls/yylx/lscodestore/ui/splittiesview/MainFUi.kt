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
        text = "flutter"
    }
    val btn4 = button {
        text = "anko"
    }
    val btn5 = button {
        text = "jetpack_compose"
    }
    val btn6 = button {
        text = "xml"
    }
    val btn7 = button {
        text = "hadrcode"
    }
    override val root = verticalLayout {
        add(btn, lParams(matchParent, wrapContent) { })
        add(btn2, lParams(matchParent, wrapContent)  { })
        add(btn3, lParams(matchParent, wrapContent)  { })
        add(btn4, lParams(matchParent, wrapContent)  { })
        add(btn5 ,lParams(matchParent, wrapContent)  { })
        add(btn6 ,lParams(matchParent, wrapContent)  { })
        add(btn7 ,lParams(matchParent, wrapContent)  { })
    }
}