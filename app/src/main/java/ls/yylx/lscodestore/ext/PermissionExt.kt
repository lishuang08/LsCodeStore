package ls.yylx.lscodestore.ext

import android.Manifest
import ls.yylx.lscodestore.base.BaseFragment


fun BaseFragment.checkAllMediaPms(granted: (granted: MutableMap<String, Boolean>) -> Unit) {
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

fun BaseFragment.checkSdPms(granted: (granted: MutableMap<String, Boolean>) -> Unit) {
    val arrPms = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    pmsCallBack = {
        granted.invoke(it)
    }


    pmsLauncher.launch(arrPms)
}


fun BaseFragment.checkCamera(granted: (granted: Boolean) -> Unit) {
    val arrPms = Manifest.permission.CAMERA

    pmCallBack = {
        granted.invoke(it)
    }


    pmLauncher.launch(arrPms)
}