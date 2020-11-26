package ls.yylx.lscodestore

import android.os.Bundle
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.basemodule.PermissionManager
import ls.yylx.lscodestore.viewmodel.GbifViewModel
import ls.yylx.lscodestore.viewmodel.Http3ViewModel
import java.nio.charset.Charset


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    val gbifVm by viewModels<GbifViewModel>()
    val http3Vm  by viewModels<Http3ViewModel>()


    val navController by lazy {
        findNavController(R.id.fragment_main_relase)
//        if (resources.getBoolean(R.bool.isModule)) {
//            findNavController(R.id.fragment_main_relase)
//        } else {
//            findNavController(R.id.fragment_main)
//        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_relase)


        var start = true
        Looper.getMainLooper().setMessageLogging {
            if (start) {
                println("start$it")
                start = false
            } else {
                println("end$it")
                start = true
            }
        }

//        gbifVm.updateSpecies()
//        http3Vm.http3()

//        if (resources.getBoolean(R.bool.isModule)) {
//            setContentView(R.layout.main_activity_relase)
//        } else {
//            setContentView(R.layout.main_activity)
//        }

        //透明状态栏
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.statusBarColor = resources.getColor(android.R.color.transparent)

//        launch {
//            delay(3000)
//
////            showNotification(PushResults("aaaaaa",3999))
//            navController.navigate(R.id.x5WebViewFragment)
//        }
//        btn_ok.setOnClickListener {
//            showBiometricPrompt()
//        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }


    data class PushResults(
        val title: String,
        val num: Int
    )


    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
//            .setNegativeButtonText("Cancel")
            .setDeviceCredentialAllowed(true)
            .setConfirmationRequired(false)

            .build()


        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)

                    Logger.e("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    val authenticatedCryptoObject = result.cryptoObject
                    val encryptedInfo: ByteArray? = result.cryptoObject?.cipher?.doFinal(
                        "adsada".toByteArray(
                            Charset.defaultCharset()
                        )
                    )


                    Logger.e(authenticatedCryptoObject.toString())

                    Logger.e("onAuthenticationSucceeded    $encryptedInfo")

                    // User has verified the signature, cipher, or message
                    // authentication code (MAC) associated with the crypto object,
                    // so you can use it in your app's crypto-driven workflows.
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Logger.e("Authentication failed")
                }
            })

//        biometricPrompt.authenticate(promptInfo , BiometricPrompt.CryptoObject())

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestMultiplePermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
        PermissionManager.onRequestPermissionsResult(requestCode, grantResults)
    }


}
