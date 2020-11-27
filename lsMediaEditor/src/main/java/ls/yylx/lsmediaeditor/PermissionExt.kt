package ls.yylx.lsmediaeditor

import android.Manifest


fun BaseAppCompatDialogFragment.checkAllMediaPms(granted: (granted: MutableMap<String, Boolean>) -> Unit) {
    val arrPms = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    pmsCallBack = {
        granted.invoke(it)
    }

    pmsLauncher.launch(arrPms)
}

fun BaseAppCompatDialogFragment.checkSdPms(granted: (granted: MutableMap<String, Boolean>) -> Unit) {
    val arrPms = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    pmsCallBack = {
        granted.invoke(it)
    }


    pmsLauncher.launch(arrPms)
}


fun BaseAppCompatDialogFragment.checkCamera(granted: (granted: Boolean) -> Unit) {
    val arrPms = Manifest.permission.CAMERA

    pmCallBack = {
        granted.invoke(it)
    }


    pmLauncher.launch(arrPms)
}