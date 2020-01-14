package ls.yylx.lscodestore

import android.annotation.SuppressLint
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*
import kotlin.properties.Delegates


class PageAdapterJob : RecyclerView.Adapter<PageAdapterJob.SearchBookViewHolder>() {
    var holder: SearchBookViewHolder? = null

    var back: ((item: String , position: Int) -> Unit)? = null

    /**  val newList= items.toMutableList()或者 val newList = items.toList() ,来复制list的值,
     * list地址改变，但是item不会改变 ，
     * val newList  = items.map { it.copy() },会修改list地址和item的地址
     *
     * 只有整个list变化的时候才会触发
     * **/
    var items: List<String> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }


    override fun getItemCount() = items.size


    fun setOnItemClickListener(item: (item: String , position: Int) -> Unit) {
        back = item
    }

    override fun onBindViewHolder(holder: SearchBookViewHolder, position: Int) {
        holder.bindTo(items[position])
        this.holder = holder
        holder.itemView.setOnClickListener {
            if (back != null) {
                back?.invoke(holder.job!!, position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookViewHolder =
        SearchBookViewHolder(parent)


    inner class SearchBookViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        view().createView(AnkoContext.create(parent.context, parent))
    ) {
        var job: String? = null
        val tv = itemView.find<TextView>(ID_TV)

//        val author = itemView.find<TextView>(txt1)

        @SuppressLint("SetTextI18n")
        fun bindTo(job: String ?) {
            this.job = job
            tv.text= job
        }

    }

    inner class view : AnkoComponent<View> {
        override fun createView(ui: AnkoContext<View>) =
            with(ui) {
                verticalLayout {

                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    textView {
                        textSizeDimen = R.dimen.text_size_16
                        id = ID_TV
                        gravity = Gravity.CENTER_VERTICAL
                        if (Build.VERSION.SDK_INT >= 23) {
                            foreground =
                               context.getDrawable(attr(R.attr.selectableItemBackgroundBorderless).resourceId)
                        }

                        setPadding(dip(16), 0, 0, 0)
                    }.lparams(matchParent, dip(40)) {

                    }

                }
            }

    }


    val ID_TV = 0X12312312
}

