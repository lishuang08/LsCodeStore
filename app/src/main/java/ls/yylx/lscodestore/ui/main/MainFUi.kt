package ls.yylx.lscodestore.ui.main

import android.content.Context
import splitties.views.dsl.core.*

class MainFUi(override val ctx: Context) : Ui {
    val btn = button {
        text = "查看"
    }


    override val root = frameLayout {
        add(btn, lParams(matchParent, wrapContent) { })
    }
}