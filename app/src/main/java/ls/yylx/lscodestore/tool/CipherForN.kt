package ls.yylx.lscodestore.tool

import android.util.Base64
import com.tencent.mmkv.MMKV
import ls.yylx.lscodestore.MyApp
import ls.yylx.lscodestore.basemodule.R
import ls.yylx.lscodestore.basemodule.base.BaseAppliacation.Companion.appCtx
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

/**
 * Create by ls
 * 新加密方式,适用于Android N之后,弃用SHA1PRNG
 * 保存mmkv
 *  2019-10-29
 */
object CipherForN {
    val mmkvEncrypted by lazy(LazyThreadSafetyMode.NONE) {
        MMKV.mmkvWithID(
            "mmap_id_test",
            MMKV.SINGLE_PROCESS_MODE,
             appCtx .getString(R.string.test_cryptkey)
        )
    }


    private val KEY_SK by lazy {
        appCtx.getString(R.string.iv_key)
    }//任意字符串,通过MD5转换32个字节

    private val ivs
        get() = mmkvEncrypted.decodeBytes("ivs")


    @Throws(Exception::class)
    fun encrypt(enctyptdata: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        if (ivs == null) {
            val newIvs = Random(233).nextBytes(ByteArray(cipher.blockSize))
            cipher.init(
                Cipher.ENCRYPT_MODE,
                generateKey(), IvParameterSpec(
                    newIvs
                )
            )
            mmkvEncrypted.encode("ivs", newIvs)
        } else {
            cipher.init(
                Cipher.ENCRYPT_MODE,
                generateKey(), IvParameterSpec(
                    ivs
                )
            )
        }
        val encrypted = cipher.doFinal(enctyptdata.toByteArray(charset("utf-8")))

        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    @Throws(Exception::class)
    fun decrypt(decryptdata: String): String {
        val cipherText = Base64.decode(decryptdata, Base64.NO_WRAP)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(
            Cipher.DECRYPT_MODE,
            generateKey(), IvParameterSpec(
                ivs
            )
        )
        val decrypted = cipher.doFinal(cipherText)
        return String(decrypted)
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun generateKey(): SecretKey? {
        var key: SecretKey? = null
        try {
            key = SecretKeySpec(
                strToMD5(
                    KEY_SK
                )!!.toByteArray(charset("UTF-8")), "AES")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return key
    }


    /**
     * 将字符串转成MD5值
     */
    fun strToMD5(string: String): String? {
        try {
            val instance: MessageDigest = MessageDigest.getInstance("MD5")//获取md5加密对象
            val digest: ByteArray = instance.digest(string.toByteArray())//对字符串加密，返回字节数组
            val sb = StringBuffer()
            for (b in digest) {
                val i: Int = b.toInt() and 0xff//获取低八位有效值
                var hexString = Integer.toHexString(i)//将整数转化为16进制
                if (hexString.length < 2) {
                    hexString = "0$hexString"//如果是一位的话，补0
                }
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return ""
        }
    }
}