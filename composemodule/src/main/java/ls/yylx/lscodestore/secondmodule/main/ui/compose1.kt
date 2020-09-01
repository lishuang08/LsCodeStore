package ls.yylx.lscodestore.secondmodule.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
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

    Column(modifier = Modifier.padding(16.dp).clickable(onClick = {
        state.invoke(State_Page0(hashMapOf()))
    })) {
        list.forEach {
            Text(text = it)
        }
        Image(image, imageModifier, contentScale = ContentScale.Crop)
    }
}
