package ls.yylx.lscodestore.secondmodule.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.tooling.preview.Preview
import ls.yylx.lscodestore.secondmodule.R

class SecondMainActivity :  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        setContent {
//            Text("Hello world!")
//        }
    }

//    @Composable
//    fun Greeting(name: String) {
//        Text (text = "Hello $name!")
//    }

//    @Preview
//    @Composable
//    fun PreviewGreeting() {
//        Greeting("Android")
//    }

}
