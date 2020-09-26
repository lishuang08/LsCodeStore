package ls.yylx.lscodestore.media

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.text.Spannable
import android.text.style.DynamicDrawableSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.scale
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.listItems
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.set
import com.google.android.material.button.MaterialButton
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.base.BaseFragment
import ls.yylx.lscodestore.basemodule.checkArrayPermissions
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class AudioFragment : BaseFragment() {

    val recordBufSize =
        AudioRecord.getMinBufferSize(
            11025,//采样率 44100、22050、11025、4000、8000
            AudioFormat.CHANNEL_IN_FRONT,//单声道 CHANNEL_IN_FRONT   双声道 CHANNEL_IN_STEREO
            AudioFormat.ENCODING_PCM_16BIT
        ) //采样大小  16bit  ENCODING_PCM_16BIT        ENCODING_PCM_8BIT

    val audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        11025,
        AudioFormat.CHANNEL_IN_FRONT,
        AudioFormat.ENCODING_PCM_16BIT,
        recordBufSize
    )
    var recording = false
    var pcmFile: File? = null
    var mbRecord: MaterialButton? = null

    var  md  :MaterialDialog ?=null
    var tvTime :TextView?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI {
        frameLayout {
            button("录音"){
                onClick {
                    checkArrayPermissions(arrayOf(Manifest.permission.RECORD_AUDIO)) {
                        if (it) {
                            showAudio()
                        }
                    }

                }
            }.lparams {
                gravity = Gravity.CENTER
            }
        }
    }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    private fun showAudio() {
        md = MaterialDialog(requireContext()).show {
            customView(view = UI {
                verticalLayout {
                    gravity = Gravity.CENTER
                    tvTime = textView {
                        text = "语音时间小于60秒"
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent) {

                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER
                        mbRecord = customView<MaterialButton> {
                            cornerRadius = dip(16)
                            text = "录音"
                            textSizeDimen = R.dimen.text_size_14
                            padding = dip(16)
                            backgroundColorResource = R.color.colorPrimary
                            onClick {
                                if (recording) {
                                    recording = false
                                    backgroundColorResource = R.color.colorPrimary
                                } else {
                                    startRecord()
                                    text = "停止"
                                    backgroundColorResource = R.color.red
                                }
                            }
                        }.lparams(wrapContent, dip(48)) {
                        }
//                        mbUpLoad = customView<MaterialButton> {
//                            cornerRadius = dip(16)
//                            text = "上传"
//                            visibility = View.GONE
//                            textSizeDimen = R.dimen.dip_14
//                            padding = dip(16)
//
//                            backgroundColorResource = R.color.green_subscribe
//                            singleClick {
//                                upFile()
//                            }
//                        }.lparams(wrapContent, dip(48)) {
//                            marginStart = dip(16)
//                        }

                    }.lparams(matchParent, wrapContent) {
                        topMargin = dip(24)
                    }


                }
            }.view)

        }
    }



    private fun startRecord() {
        launch(Dispatchers.IO) {
            recording = true
            pcmFile = File(
                requireContext().externalCacheDir?.path,
                "audio_${System.currentTimeMillis()}.pcm"
            )

            audioRecord.startRecording()
            val jobCount = launch {
                repeat(60) {
                    launch(Dispatchers.Main) {
                        mbRecord?.text = "${(60 - it)}s后停止"
                    }

                    delay(1000)
                }
                recording = false
            }

            val bytes = ByteArray(recordBufSize)
            try {
                val fileOutputStream = FileOutputStream(pcmFile)
                while (recording) {
                    audioRecord.read(
                        bytes,
                        0,
                        bytes.size
                    ) //读取流
                    fileOutputStream.write(bytes)
                    fileOutputStream.flush()
                }

                Logger.e("stop")
                audioRecord.stop() //停止录制
                fileOutputStream.flush()
                fileOutputStream.close()

                jobCount.cancel()


                launch(Dispatchers.Main) {
                    tvTime?.text = "录音文件：${pcmFile?.path}"
                    mbRecord?.text = "重新录制"
//                    mbUpLoad?.visibility = View.VISIBLE
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                audioRecord.stop()

                Logger.e(e.toString())
            } catch (e: IOException) {
                e.printStackTrace()
                Logger.e(e.toString())
            }


        }
    }


}
