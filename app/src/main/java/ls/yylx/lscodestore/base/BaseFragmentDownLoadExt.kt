package ls.yylx.lscodestore.base

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.View
import androidx.core.content.FileProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.basemodule.PreviewItem
import ls.yylx.lscodestore.basemodule.network.ApiResponse
import ls.yylx.lscodestore.basemodule.network.DownloadProgressInterceptor
import ls.yylx.lscodestore.basemodule.network.SingleApiCallback
import ls.yylx.lscodestore.basemodule.network.SingleRetrofit
import ls.yylx.lscodestore.tool.Tools
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.jetbrains.anko.dimen
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.util.*

fun BaseDownLoadFragment.openDownLoadFile(
    item: PreviewItem
) {
    val file =
        File(requireContext().getExternalFilesDir(null)?.path + "/" + item.name)


    val downInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())
            return originalResponse.newBuilder()
                .body(
                    DownloadProgressInterceptor.DownloadProgressResponseBody(
                        originalResponse.body!!,
                        object :
                            DownloadProgressInterceptor.DownloadProgressListener {
                            override fun update(
                                bytesRead: Long,
                                contentLength: Long,
                                done: Boolean
                            ) {
                                launch(Dispatchers.Main) {
                                    if (done) {
                                        if (item.size <= 0) item.size = bytesRead
                                        downloaded(file, item)
                                    } else {
                                        tvPb?.run {
                                            val total =
                                                if (item.size > 0) ("/ " + Tools.getSize(
                                                    item.size.toDouble()
                                                )) else ""
                                            text =
                                                "下载进度：${Tools.getSize(
                                                    bytesRead.toDouble()
                                                )} $total"
                                        }
                                    }
                                }
                            }
                        }
                    )
                )
                .build()
        }
    }

    val retrofitDownloadApi by lazy {
        Retrofit.Builder()
            .client(
                SingleRetrofit.getDownloadBuilder(
                    downInterceptor
                )
            )
            .baseUrl(
                 "http://www.gbif.com"
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiResponse::class.java)
    }

    md = MaterialDialog(requireContext()).show {
        cancelable(false)
        customView(view = requireContext().verticalLayout {
            textView {
                gravity = Gravity.CENTER_HORIZONTAL
                text = item.name
                textSize  = dimen( R.dimen.text_size_16).toFloat()
            }
            tvPb = textView {
                gravity = Gravity.CENTER_HORIZONTAL
                text = "等待下载开始 "
                textSize  = dimen( R.dimen.text_size_16).toFloat()
            }

            negativeButton(text = "取消") {

            }
        }
        )
    }

    if (file.length() == item.size) {
        downloaded(file, item)
    } else {
        launch(Dispatchers.IO) {
            retrofitDownloadApi.download(item.path)
                .enqueue(object : SingleApiCallback<ResponseBody>() {
                    override fun onMyError(
                        response: retrofit2.Response<ResponseBody>?,
                        msg: String
                    ) {
                        super.onMyError(response, msg)

                        pb?.visibility = View.INVISIBLE

                        tvPb?.run {
                            text = "下载失败，请重试"
                        }
                        Logger.e("下载失败，请重试")
                    }

                    override fun onMySuccess(response: retrofit2.Response<ResponseBody>) {
                        super.onMySuccess(response)
                        val writeSuccess =
                            writeResponseBodyToDisk(response.body()!!, file)

                        if (writeSuccess) {
                            launch(Dispatchers.Main) {
                                downloaded(file, item)
                            }
                        }
                    }
                })
        }
    }
}


fun BaseDownLoadFragment.downloaded(file: File, item: PreviewItem) {
    launch(Dispatchers.Main) {
        pb?.visibility = View.INVISIBLE
        tvPb?.run {
            text = """|下载完成
                        |文件大小：${Tools.getSize(item.size.toDouble())}""".trimMargin()
        }
        md?.run {
            positiveButton(text = "打开文件") {
                openFile(
                    file,
                    item.ext.toLowerCase(Locale.CHINESE)
                )
                back?.invoke(file)
            }
        }
    }
}


private val DATA_TYPE_ALL = "*/*";//未指定明确的文件类型，不能使用精确类型的工具打开，需要用户选择
private val DATA_TYPE_APK = "application/vnd.android.package-archive";
private val DATA_TYPE_VIDEO = "video/*";
private val DATA_TYPE_AUDIO = "audio/*";
private val DATA_TYPE_HTML = "text/html";
private val DATA_TYPE_IMAGE = "image/*";
private val DATA_TYPE_PPT = "application/vnd.ms-powerpoint";
private val DATA_TYPE_EXCEL = "application/vnd.ms-excel";
private val DATA_TYPE_WORD = "application/msword";
private val DATA_TYPE_CHM = "application/x-chm";
private val DATA_TYPE_TXT = "text/plain";
private val DATA_TYPE_PDF = "application/pdf";


fun BaseDownLoadFragment.openFile(file: File, type: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    var uri: Uri? = null

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        uri = Uri.fromFile(file)
    } else {
        uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".fileProvider",
            file
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    val fileType = when (type) {
        "pdf" -> DATA_TYPE_PDF
        "doc", "docx" -> DATA_TYPE_WORD
        "xls" -> DATA_TYPE_EXCEL
        "ppt" -> DATA_TYPE_PPT
        else -> DATA_TYPE_ALL
    }

    intent.setDataAndType(uri, fileType)
    startActivity(Intent.createChooser(intent, null));
}


private fun BaseDownLoadFragment.writeResponseBodyToDisk(body: ResponseBody, file: File): Boolean {
    var success = false
    try {

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val fileSize = body.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream = body.byteStream()
            outputStream = FileOutputStream(file)
            while (true) {
                val read = inputStream.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()
            }
            outputStream.flush()
            success = true
        } catch (e: IOException) {
            success = false
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
        success = false
    }

    return success
}
