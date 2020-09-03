package ls.yylx.lscodestore.firstmodule

import android.app.Activity
import android.content.Context
import android.view.ViewManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

@PublishedApi
internal object LsAnkoFactoriesViews {

    val VIEWPAGER2 = { ctx: Context -> ViewPager2(ctx) }
    val MATERIAL_BUTTON = { ctx: Context -> MaterialButton(ctx) }

}


inline fun ViewManager.viewPager2(theme: Int = 0): ViewPager2 = viewPager2(theme) {}
inline fun ViewManager.viewPager2(
    theme: Int = 0,
    init: (@AnkoViewDslMarker ViewPager2).() -> Unit
): ViewPager2 {
    return ankoView(LsAnkoFactoriesViews.VIEWPAGER2, theme, init)
}


inline fun Context.viewPager2(theme: Int = 0): ViewPager2 = viewPager2(theme) {}
inline fun Context.viewPager2(
    theme: Int = 0,
    init: (@AnkoViewDslMarker ViewPager2).() -> Unit
): ViewPager2 {
    return ankoView(LsAnkoFactoriesViews.VIEWPAGER2, theme, init)
}


inline fun Activity.viewPager2(theme: Int = 0): ViewPager2 = viewPager2(theme) {}
inline fun Activity.viewPager2(
    theme: Int = 0,
    init: (@AnkoViewDslMarker ViewPager2).() -> Unit
): ViewPager2 {
    return ankoView(LsAnkoFactoriesViews.VIEWPAGER2, theme, init)
}


inline fun ViewManager.materialButton(theme: Int = 0): MaterialButton = materialButton(theme) {}
inline fun ViewManager.materialButton(
    theme: Int = 0,
    init: (@AnkoViewDslMarker MaterialButton).() -> Unit
): MaterialButton {
    return ankoView(LsAnkoFactoriesViews.MATERIAL_BUTTON, theme, init)
}


inline fun Context.materialButton(theme: Int = 0): MaterialButton = materialButton(theme) {}
inline fun Context.materialButton(
    theme: Int = 0,
    init: (@AnkoViewDslMarker MaterialButton).() -> Unit
): MaterialButton {
    return ankoView(LsAnkoFactoriesViews.MATERIAL_BUTTON, theme, init)
}


inline fun Activity.materialButton(theme: Int = 0): MaterialButton = materialButton(theme) {}
inline fun Activity.materialButton(
    theme: Int = 0,
    init: (@AnkoViewDslMarker MaterialButton).() -> Unit
): MaterialButton {
    return ankoView(LsAnkoFactoriesViews.MATERIAL_BUTTON, theme, init)
}
