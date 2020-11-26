package ls.yylx.lsmediaeditor

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.support.v4.UI


class ShowImageDialogFragment(val title: String, val filePath: String = "", val imgId: Int = 0) :
    BaseAppCompatDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI {
        verticalLayout {
            backgroundColorResource = android.R.color.white
            linearLayout {
                imageView {
                    padding = dip(12)
                    imageResource = R.drawable.ic_back
                    setOnClickListener { dismiss() }
                }.lparams(dip(48), dip(48)) {
                    gravity = Gravity.CENTER_VERTICAL
                }
                textView {
                    text = title
                    textSize = 16f
                    typeface = Typeface.DEFAULT_BOLD
                    gravity = Gravity.CENTER
                    textColor = Color.parseColor("#000000")
                }.lparams(matchParent, matchParent) {
                    marginEnd = dip(48)
                    gravity = Gravity.CENTER_VERTICAL
                }
            }.lparams(matchParent, dip(56))

            customView<ScaleImageView> {
                scaleType = ImageView.ScaleType.FIT_CENTER

                Glide.with(this).load(if (filePath.isEmpty()) imgId else filePath).into(this)
            }.lparams(matchParent, matchParent)
        }
    }.view


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
