import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import java.io.File
import java.math.BigDecimal
import java.util.*


object Tools {
    /**
     * 获取大小
     *
     * @param size
     * @return
     */
    fun getSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            val result0 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result0.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "B"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "K"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "M"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "G"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T"
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            fileList?.forEach {
                size += if (it.isDirectory) getFolderSize(it) else it.length()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            val result0 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result0.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "B"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }


    /**
     * 递归删除文件和文件夹
     * @param file 要删除的根目录
     */
    fun DeleteDir(file: File) {
        if (file.isFile) {
            file.delete()
            return
        }
        if (file.isDirectory) {
            val childFile = file.listFiles()
            if (childFile == null || childFile.isEmpty()) {
                file.delete()
                return
            }
            for (f in childFile) {
                DeleteDir(f)
            }
            file.delete()
        }
    }


    //
    fun substringStr(list: List<String>): String {
        var strReturn = ""
        when {
            list.isEmpty() -> ""
            list.size == 1 -> strReturn = list[0]
            else -> {
                var ids = ""
                list.forEach {
                    ids += "$it,"
                }
                strReturn = ids.run { substring(0..(ids.length - 2)) }
            }
        }

        return strReturn
    }


    fun substringStrPath(list: List<String>): String {
        var strReturn = ""
        when {
            list.isEmpty() -> ""
            list.size == 1 -> strReturn = list[0]
            else -> {
                var path = ""
                list.forEach {
                    path += "$it，"
                }
                strReturn = path.run { substring(0..(path.length - 2)) }
            }
        }

        return strReturn
    }


    fun isEmulator(ctx:Context ): Boolean {
        val url = "tel:" + "123456"
        val intent = Intent()
        intent.data = Uri.parse(url)
        intent.action = Intent.ACTION_DIAL
        // 是否可以处理跳转到拨号的 Intent
        val canResolveIntent =
            intent.resolveActivity(ctx.packageManager) != null
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL.equals("unknown", ignoreCase = true)
                || Build.SERIAL.equals("android", ignoreCase = true)
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT || (Objects.requireNonNull(
            ctx.getSystemService(Context.TELEPHONY_SERVICE)
        ) as TelephonyManager)
            .networkOperatorName.toLowerCase() == "android" || !canResolveIntent)
    }

}