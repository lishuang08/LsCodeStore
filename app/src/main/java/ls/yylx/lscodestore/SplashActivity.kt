package ls.yylx.lscodestore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.jetbrains.anko.frameLayout

class SplashActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            finish()
            return
        }
       frameLayout {

       }


        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}
