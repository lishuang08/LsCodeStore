package ls.yylx.lsmediaeditor

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDialogFragment

open class BaseAppCompatDialogFragment  : AppCompatDialogFragment() {
    /**默认添加registerForActivityResult方式，增加权限请求**/
    val pmsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it ->
            pmsCallBack?.invoke(it)
        }
    var pmsCallBack: ((MutableMap<String, Boolean>) -> Unit?)? = null


    val pmLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { it ->
            pmCallBack?.invoke(it)
        }
    var pmCallBack: ((Boolean) -> Unit?)? = null

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
}