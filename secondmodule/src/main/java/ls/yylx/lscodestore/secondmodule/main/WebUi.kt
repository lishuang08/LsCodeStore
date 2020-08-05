package ls.yylx.lscodestore.secondmodule.main

import android.content.Context
import android.view.View
import android.webkit.WebView
import ls.yylx.lscodestore.secondmodule.R
import org.jetbrains.anko.textColorResource
import splitties.views.dsl.constraintlayout.*
import splitties.views.dsl.core.*

class WebUi(
    override val ctx: Context
) : Ui {
    val v0 = textView {
        text = "androidView"
        textColorResource = R.color.black
    }
    val web = WebView(ctx).apply {

    }

    override val root: View = constraintLayout {
        lParams(matchParent, matchParent) { }
        add(v0, lParams(matchParent, wrapContent) {
            topToTop = parentId
        })
        add(web, lParams(matchParent, matchConstraints) {
            topToBottomOf(v0)
            bottomToBottom = parentId
        })
    }
}