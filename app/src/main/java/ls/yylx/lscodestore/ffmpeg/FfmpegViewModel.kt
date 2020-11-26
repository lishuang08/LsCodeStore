package ls.yylx.lscodestore.ffmpeg

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import ls.yylx.lscodestore.MyApp
import java.io.File

class FfmpegViewModel(app: Application) : AndroidViewModel(app),
    CoroutineScope by CoroutineScope(Dispatchers.IO) {
    val aptCtx by lazy {
        getApplication<MyApp>()
    }
    var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun ffmpeg() {
        Logger.e("ffmpeg")
        job = launch {
            val file = File(aptCtx.externalCacheDir, "1.mp4")

            while (isActive ) {

                Logger.e("execute  ${file.exists()} ${file.path}")

                var str =
                    "-re -i ${file.path} -vcodec libx264 -acodec aac -f flv -strict -2 rtmp://42.192.166.159/ls/mylive="

                Logger.e("execute  $str")

                val rc =
                    FFmpeg.execute(str)

                Logger.e(rc.toString())

                if (rc == Config.RETURN_CODE_SUCCESS) {
                    Logger.e("Command execution completed successfully.")
                } else if (rc == Config.RETURN_CODE_CANCEL) {
                    Logger.e("Command execution cancelled by user.")
                } else {
                    job?.cancel()
                    Logger.e(

                        String.format(
                            "Command execution failed with rc=%d and the output below.",
                            rc
                        )
                    )
                    Config.printLastCommandOutput(Log.INFO)
                }
            }
        }

    }
}