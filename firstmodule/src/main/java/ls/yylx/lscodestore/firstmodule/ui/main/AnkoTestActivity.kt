package ls.yylx.lscodestore.firstmodule.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class AnkoTestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            textView("Hello world!")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
            textView("asadad")
        }
    }
}