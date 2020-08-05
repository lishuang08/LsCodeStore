package ls.yylx.lscodestore.secondmodule.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.padding
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.viewinterop.AndroidView
import org.jetbrains.anko.UI
import org.jetbrains.anko.webView

class SecondMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainPage()
        }
    }

    @Composable
    fun mainPage() {
        ScrollableColumn(modifier = Modifier.padding(16.dp)) {
            Text("Hello world!")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
            Text("asadad")
//            addAndroidView()
            addAnkoAndroidView()
        }
    }


    @Preview
    @Composable
    fun addAnkoAndroidView() {
        AndroidView(view = UI {
            webView  {
                loadUrl("https://www.qq.com")
            }
        }.view, modifier = Modifier.padding(16.dp))
    }


    @Composable
    fun addAndroidView() {
        val v = WebUi(this)
        v.web.loadUrl("https://www.qq.com")
        AndroidView(view = v.root, modifier = Modifier.padding(16.dp))
    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Composable
    fun itemColumn() {
        Column {
            ConstraintLayout {

            }
        }

    }


    @Composable
    fun PreviewGreeting() {
        Greeting("aaa")
    }


}
