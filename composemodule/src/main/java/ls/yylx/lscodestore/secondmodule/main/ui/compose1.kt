package ls.yylx.lscodestore.secondmodule.main.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import ls.yylx.lscodestore.secondmodule.R
import ls.yylx.lscodestore.secondmodule.base.HomeState
import ls.yylx.lscodestore.secondmodule.base.State_Page0


@Composable
fun columnView(list: List<String>, state: (HomeState) -> Unit) {

    val imageModifier = Modifier
        .preferredHeight(240.dp)//指定图片的高度
        .fillMaxWidth()//填充
        .clip(shape = RoundedCornerShape(4.dp)) //裁剪：圆角

    val image = imageResource(R.mipmap.cos_2b)

    LazyColumnFor(items = list, modifier = Modifier.padding(16.dp).clickable(onClick = {
        state.invoke(State_Page0(hashMapOf()))
    })) {

//        item.forEach {
            Text(text = it)
//        }
    }
    Image(image, imageModifier, contentScale = ContentScale.Crop)
}


val  pages  = listOf("page0","page1","page2","page3","page4","page5","page6","page7","page8","page9")

@Preview
@Composable
fun composeVp () {
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
        when (mState.value) {
            else -> {
                Text(text = pages[mState.value])
            }
        }
    }

}
