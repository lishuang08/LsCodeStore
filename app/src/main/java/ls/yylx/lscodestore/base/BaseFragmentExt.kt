package ls.yylx.lscodestore.base

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import ls.yylx.lscodestore.R
import splitties.alertdialog.appcompat.alertDialog
import splitties.dimensions.dip
import splitties.experimental.InternalSplittiesApi
import splitties.resources.dimenPxSize
import splitties.views.dsl.constraintlayout.constraintLayout
import splitties.views.dsl.constraintlayout.lParams
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.view
import splitties.views.padding


/**
 * 收起软键盘并设置提示文字
 */
fun Fragment.collapseSoftInputMethod() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity?.window!!.decorView.windowToken, 0)
}


fun Fragment.showPopWindow(pop: PopupWindow?, v: View): PopupWindow {

    var popupWindow = pop
    if (popupWindow == null) {
        popupWindow = PopupWindow(
            v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            isFocusable = false
            isOutsideTouchable = false

            inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            update()//刷新状态

            animationStyle =
                R.style.AnimationFunction//设置PopupWindow弹出窗体动画效果

            backgroundAlpha(0.5f)
            setOnDismissListener { backgroundAlpha(1F) }

        }.apply {
            showAtLocation(view, Gravity.BOTTOM, 0, 0)
        }
    } else {
        popupWindow.contentView = v
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)
        backgroundAlpha(0.5f)
    }
    return popupWindow
}


fun Fragment.backgroundAlpha(bgAlpha: Float) {
    val lp = requireActivity().window.attributes
    lp.alpha = bgAlpha //0.0-1.0
    requireActivity().window.attributes = lp
}


fun Fragment.showPopWindowTop(pop: PopupWindow?, v: View): PopupWindow {
    var popupWindow = pop
    if (popupWindow == null) {
        popupWindow = PopupWindow(
            v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            isOutsideTouchable = true
            inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            update()//刷新状态
            val dw = ColorDrawable(0)//实例化一个ColorDrawable颜色为半透明
            this.setBackgroundDrawable(dw) //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//            animationStyle = R.style.AnimationFunction//设置PopupWindow弹出窗体动画效果
            backgroundAlpha(0.75f)
            setOnDismissListener { backgroundAlpha(1F) }
        }.apply {
            showAtLocation(view, Gravity.TOP, 0, requireContext().dip (56 + 16)  )
        }
    } else {
        popupWindow.contentView = v
        popupWindow.showAtLocation(view, Gravity.TOP, 0,  requireContext().dip(56 + 16) )
        backgroundAlpha(0.75f)
    }
    return popupWindow
}


fun Fragment.showChoicePopWindow(pop: PopupWindow?, v: View): PopupWindow {
    var popupWindow = pop
    if (popupWindow == null) {
        popupWindow = PopupWindow(
            v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            isOutsideTouchable = true

            inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            update()//刷新状态
            val dw = ColorDrawable(0)//实例化一个ColorDrawable颜色为半透明
            this.setBackgroundDrawable(dw) //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//            animationStyle = R.style.AnimationFunction//设置PopupWindow弹出窗体动画效果
            backgroundAlpha(0.7f)
            setOnDismissListener {
                backgroundAlpha(1f)
            }
        }.apply {
            showAtLocation(view, Gravity.TOP or Gravity.END, 0, requireContext().dip(56 + 16) )
        }
    } else {
        popupWindow.contentView = v
        popupWindow.showAtLocation(view, Gravity.TOP or Gravity.END, 0, requireContext().dip(56 + 16) )
        backgroundAlpha(0.7f)
    }
    return popupWindow
}


@InternalSplittiesApi
fun BaseFragment.alertPhoto(path: String) {
    requireContext().alertDialog {
        setView(requireContext().constraintLayout {
            view<PhotoView> {
                Glide.with(requireContext()).load(path).centerInside().into(this)
                lParams(matchParent, 0)  {
                    dimensionRatio = "h,1:1.5"
                    padding = dip(4)
                }
            }
        })
    }.show()
}

//fun Fragment.openFile(filePath: String) {
//    val file = File(filePath)
//    if (!file.exists()) {
//        //如果文件不存在
//        toast("打开失败，原因：文件已经被移动或者删除")
//        return
//    }
//    /* 取得扩展名 */
//    var end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(
//        Locale.getDefault()
//    );
//    /* 依扩展名的类型决定MimeType */
//    var   intent:Intent  = null
//    if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals(
//            "wav"
//        )
//    ) {
//        Intent.FILE
//        intent = intentFor<>(filePath, DATA_TYPE_AUDIO);
//    } else if (end.equals("3gp") || end.equals("mp4")) {
//        intent = generateVideoAudioIntent(filePath, DATA_TYPE_VIDEO);
//    } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_IMAGE);
//    } else if (end.equals("apk")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_APK);
//    } else if (end.equals("html") || end.equals("htm")) {
//        intent = getHtmlFileIntent(filePath);
//    } else if (end.equals("ppt")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_PPT);
//    } else if (end.equals("xls")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_EXCEL);
//    } else if (end.equals("doc")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_WORD);
//    } else if (end.equals("pdf")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_PDF);
//    } else if (end.equals("chm")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_CHM);
//    } else if (end.equals("txt")) {
//        intent = generateCommonIntent(filePath, DATA_TYPE_TXT);
//    } else {
//        intent = generateCommonIntent(filePath, DATA_TYPE_ALL);
//    }
//    startActivity(intent)
//}
