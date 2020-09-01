package ls.yylx.lscodestore.basemodule.rvadapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ls.yylx.lscodestore.basemodule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import kotlin.properties.Delegates

//
//class RvAdpInput : RecyclerView.Adapter<RvAdpInput.InputViewHolder>(),
//    CoroutineScope by MainScope() {
//    /**  val newList= items.toMutableList()或者 val newList = items.toList() ,来复制list的值,
//     * list地址改变，但是item不会改变 ，
//     * val newList  = items.map { it.copy() },会修改list地址和item的地址
//     *
//     * 只有整个list变化的时候才会触发
//     * **/
//    var items: List<InputItem> by Delegates.observable(emptyList()) { _, old, new ->
//        autoNotify(old, new) { o, n -> o == n }
//    }
//
//    var holder: InputViewHolder? = null
//
//    var back: ((item: InputItem, position: Int) -> Unit)? = null
//
//
//    override fun getItemCount() = items.size
//
//    fun onClickTiet(item: (item: InputItem, position: Int) -> Unit) {
//        back = item
//    }
//
//
//    override fun onBindViewHolder(holder: InputViewHolder, position: Int) {
//        holder.run {
//            val item = items[position]
//
//            bindTo(item, position)
//
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InputViewHolder(parent)
//
//
//    inner class InputViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
//        InputView().createView(AnkoContext.create(parent.context, parent))
//    ) {
//
//        lateinit var item: InputItem
//        lateinit var tilInput: TextInputLayout
//        lateinit var tietInput: TextInputEditText
//
//        fun bindTo(item: InputItem, position: Int) {
//            tilInput = itemView.find<TextInputLayout>(ID_TIL_INPUT).apply {
//                when (item.paramsName) {
//                    "fixedPhone", "address", "postcode" -> {
//                        hint = "请输入${item.name}"
//                    }
//                    else -> {
//                        hint = buildSpannedString {
//                            append("请输入${item.name}")
//                            color(Color.RED ){
//                                append( "*")
//                            }
//                        }
//                    }
//                }
//            }
//
//
//
//            tietInput = itemView.find<TextInputEditText>(ID_TIET_INPUT).apply {
//                inputType = item.inputType
//                imeOptions =
//                    if (position == items.lastIndex) EditorInfo.IME_ACTION_DONE else EditorInfo.IME_ACTION_NEXT
//
//                item.keyListener?.let {
//                    keyListener = it
//                }
//
//                item.filters?.let {
//                    filters = it
//                }
//
//                maxEms = item.maxLength
//
//                if (item.name == "是否公开" || item.name == "内容类型") {
//                    isFocusable = false
//                    singleClick {
//                        if (back != null) {
//                            back?.invoke(item, position)
//                        }
//                    }
//                } else {
//                    isFocusable = true
//                    isFocusableInTouchMode = true
//                }
//            }
//            item.tiet = tietInput
//            this.item = item
//        }
//    }
//
//
//    inner class InputView : AnkoComponent<View> {
//        override fun createView(ui: AnkoContext<View>) =
//            with(ui) {
//                textInputLayout {
//                    id = ID_TIL_INPUT
//                    layoutParams = LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                    ).apply {
//                        topMargin = dip(16)
//                        marginStart = dip(16)
//                        marginEnd = dip(16)
//                    }
//
//                    textInputEditText {
//                        textSizeDimen = R.dimen.text_size_14
//                        id = ID_TIET_INPUT
////                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, 0, 0)
////                        compoundDrawablePadding = dip(12)
//
//                    }
//                }
//            }
//    }
//
//
//    val ID_TIL_INPUT = 0X010000001
//    val ID_TIET_INPUT = 0X010000002
//
//}