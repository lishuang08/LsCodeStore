package ls.yylx.lscodestore.ffmpeg

import android.app.Application
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ls.yylx.lscodestore.MyApp
import java.io.File
import java.io.FileNotFoundException
import java.util.*


class FfmpegViewModel(app: Application) : AndroidViewModel(app),
    CoroutineScope by CoroutineScope(Dispatchers.IO) {

    val mediaCodec by lazy {
        getVideoMediaCodec()
    }

    private fun getVideoMediaCodec(): MediaCodec? {
        val format: MediaFormat = MediaFormat.createVideoFormat("video/av01", 720, 1280)
        //设置颜色格式
        format.setInteger(
            MediaFormat.KEY_COLOR_FORMAT,
            MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
        )
        //设置比特率(设置码率，通常码率越高，视频越清晰)
        format.setInteger(MediaFormat.KEY_BIT_RATE, 720 * 1280)
        //设置帧率
        format.setInteger(MediaFormat.KEY_FRAME_RATE, 10)
        //关键帧间隔时间，通常情况下，你设置成多少问题都不大。
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 10)
        // 当画面静止时,重复最后一帧，不影响界面显示
        format.setLong(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, 1000000 / 45)
        format.setInteger(
            MediaFormat.KEY_BITRATE_MODE,
            MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR
        )
        //设置复用模式
        format.setInteger(
            MediaFormat.KEY_COMPLEXITY,
            MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR
        )
        var mediaCodec: MediaCodec? = null
        try {
            mediaCodec = MediaCodec.createEncoderByType("video/av01")
            mediaCodec.configure(
                format,
                null,
                null,
                MediaCodec.CONFIGURE_FLAG_ENCODE
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            if (mediaCodec != null) {
                mediaCodec.reset()
                mediaCodec.stop()
                mediaCodec.release()
                mediaCodec = null
            }
        }
        return mediaCodec
    }


    var executionId = 0L

    val aptCtx by lazy {
        getApplication<MyApp>()
    }
    var job: Job? = null
    val file1 = File(aptCtx.externalCacheDir, "1.mp4")
    val file2 = File(aptCtx.externalCacheDir, "2.mp4")


    var arrCmd = arrayOf<String>()


    var stop = false


    override fun onCleared() {
        super.onCleared()

    }


    fun stopFF() {
        stop = true
        FFmpeg.cancel(executionId)
        job?.cancel()
    }

    fun safUriToFFmpegPath(uri: Uri): String {
        var path = ""
        try {
            val parcelFileDescriptor: ParcelFileDescriptor? =
                aptCtx.contentResolver.openFileDescriptor(uri, "r")
            parcelFileDescriptor?.let {
                path = String.format(Locale.getDefault(), "pipe:%d", it.fd)
            }
        } catch (e: FileNotFoundException) {

        }

        return path
    }

    fun vf() {

//        val info = FFprobe.getMediaInformation(file1.path)

        arrCmd = arrayOf(
            "-y",

            "-i",
            file1.path,
            "-vf",
            "scale=1280:1920*1280/1080,crop=1280:720",
//            "-thread",
//            "5",
//            "-preset",
//            "ultrafast",
            file2.path
        )

        launch {



            val rc = FFmpeg.execute(arrCmd)

            when (rc) {
                Config.RETURN_CODE_SUCCESS -> {
                    Logger.e("Command execution completed successfully.")
                }
                Config.RETURN_CODE_CANCEL -> {
                    Logger.e("Command execution cancelled by user.")
                }
                else -> {
                    Logger.e(
                        String.format(
                            "Command execution failed with rc=%d and the output below.",
                            rc
                        )
                    )
                    Config.printLastCommandOutput(Log.INFO)
                }
            }
            Logger.e(rc.toString())
        }


    }


    fun startFF() {
        stop = false
        Logger.e("ffmpeg")
        job = launch {

            arrCmd = arrayOf(
                "-re",
                "-i",
                file2.path,
                "-vcodec",
                "libx264",
                "-acodec",
                "aac",
                "-f",
                "flv",
                "-strict",
                "-2",
                "rtmp://42.192.166.159/ls/mylive=",
            )


            Logger.e("execute  $arrCmd")

//            while (!stop) {
            val rc = FFmpeg.execute(arrCmd)

            when (rc) {
                Config.RETURN_CODE_SUCCESS -> {
                    Logger.e("Command execution completed successfully.")
                }
                Config.RETURN_CODE_CANCEL -> {
                    stop = true
                    Logger.e("Command execution cancelled by user.")
                }
                else -> {
                    stop = true
                    Logger.e(
                        String.format(
                            "Command execution failed with rc=%d and the output below.",
                            rc
                        )
                    )
                    Config.printLastCommandOutput(Log.INFO)
                }
            }
            Logger.e(rc.toString())

//            }
        }

    }
}