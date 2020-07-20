package ls.yylx.lscodestore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ls.yylx.lscodestore.secondmodule.main.SecondMainActivity
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.frameLayout
import splitties.views.dsl.core.setContentView


class SplashActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            finish()
            return
        }
        setContentView(object : Ui {
            override val ctx: Context = this@SplashActivity

            override val root: View = frameLayout {

            }
        })
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}
