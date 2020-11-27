package ls.yylx.lsmediaeditor

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snack(str :String ){
    Snackbar.make(requireView(), str, Snackbar.LENGTH_SHORT)
        .show()
}