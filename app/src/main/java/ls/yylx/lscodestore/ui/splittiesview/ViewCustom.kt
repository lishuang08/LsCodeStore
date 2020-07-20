package ls.yylx.lscodestore.ui.splittiesview

import android.content.Context
import splitties.views.dsl.core.*

class ViewCustom(override val ctx: Context) : Ui {


    override val root = verticalLayout {
        add(textView {  }, lParams(matchParent, matchParent) { })
        lParams(matchParent,matchParent) {  }
    }
}