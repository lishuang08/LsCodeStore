package ls.yylx.lscodestore.secondmodule.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.ui.tooling.preview.Preview
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
        AndroidView({
            UI { webView {
                loadUrl("https://www.qq.com")
            } }.view
        }, modifier = Modifier.padding(16.dp)) {

        }
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
