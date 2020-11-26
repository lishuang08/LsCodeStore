package ls.yylx.lscodestore.secondmodule.main

import WebComponent
import WebContext
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ls.yylx.lscodestore.basemodule.GbifRoomViewModel
import ls.yylx.lscodestore.basemodule.db.Specie
import ls.yylx.lscodestore.secondmodule.base.*
import ls.yylx.lscodestore.secondmodule.main.ui.columnView
import ls.yylx.lscodestore.secondmodule.main.ui.pages
import org.jetbrains.anko.UI
import org.jetbrains.anko.webView

class ComposeMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            mainPage()

        }
    }


    val gbifVm by viewModels<GbifRoomViewModel>()


    @Composable
    fun TodoScreen(
        items: List<Specie>,
        onAddItem: (Specie) -> Unit,
        onRemoveItem: (Specie) -> Unit
    ) {

    }


    @Composable
    fun addAnkoAndroidView() {
        AndroidView({
            UI {
                webView {
                    loadUrl("https://www.qq.com")
                }
            }.view
        }, modifier = Modifier.padding(16.dp)) {

        }
    }

   
}

val list by lazy(LazyThreadSafetyMode.NONE) {
    listOf(
        "1.Jetpack Compose is a modern toolkit for building native Android UI. Jetpack Compose simplifies and accelerates UI development on Android with less code, powerful tools, and intuitive Kotlin APIs.",
        "2.In this tutorial, you'll build a simple UI component with declarative functions. You won't be editing any XML layouts or directly creating the UI widgets. Instead, you will call Jetpack Compose functions to say what elements you want, and the Compose compiler will do the rest.",
        "3.Jetpack Compose is built around composable functions. These functions let you define your app's UI programmatically by describing its shape and data dependencies, rather than focusing on the process of the UI's construction. To create a composable function, just add the @Composable annotation to the function name."
    )
}


@Composable
fun mainPage() {
    var (state, setState) = remember { mutableStateOf<HomeState>(State_Home(hashMapOf())) }

    when (state) {
        is State_Home -> {
            ScrollableColumn(modifier = Modifier.padding(16.dp)) {
                columnView(list, setState)
            }


        }
        is State_Page0 -> {
            ScrollableColumn(modifier = Modifier.padding(16.dp)) {
                WebComponent("http://www.qq.com", webContext = WebContext())
            }
        }
        is State_Page1 -> {

        }
        is State_Page2 -> {

        }
        is State_Page3 -> {

        }


    }
}

@Composable
fun vpMain() {
    MaterialTheme {
        val mState = remember { mutableStateOf(0) }

        ScrollableColumn() {
            ScrollableRow() {
                pages.forEachIndexed { index, s ->
                    Button({
                        mState.value = index
                    }, Modifier.padding(16.dp)) {
                        Text(text = s)
                    }
                }
            }

        }

    }
}

inline fun show(block: () -> Unit) {
    println("infunction")
    block()
}

fun main(){
    for(i in 1..100){
        show {
            println("inline")
        }
    }
}