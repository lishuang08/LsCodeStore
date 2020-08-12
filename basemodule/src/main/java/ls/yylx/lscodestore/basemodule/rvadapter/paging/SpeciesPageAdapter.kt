package ls.yylx.lscodestore.basemodule.rvadapter.paging

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ls.yylx.lscodestore.basemodule.R
import ls.yylx.lscodestore.basemodule.db.Specie
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView


val diffCallback = object : DiffUtil.ItemCallback<Specie>() {
    override fun areItemsTheSame(oldItem: Specie, newItem: Specie): Boolean =
        oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: Specie, newItem: Specie): Boolean =
        oldItem == newItem
}


class SpeciesPageAdapter :
    PagingDataAdapter<Specie, SpeciesPageAdapter.SpecieViewHolder>(diffCallback) {

    var holder: SpecieViewHolder? = null

    var back: ((item: Specie, position: Int) -> Unit)? = null

    val view by lazy {
        sAdpView()
    }


    fun setOnItemClickListener(item: (item: Specie, position: Int) -> Unit) {
        back = item
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        holder.bindTo(getItem(position))
        this.holder = holder
        holder.itemView.setOnClickListener {
            if (back != null) back!!(holder.specie!!, position)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder =
        SpecieViewHolder(parent)


    inner class SpecieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        view.createView(AnkoContext.create(parent.context, parent))
    ) {
        var specie: Specie? = null


        @SuppressLint("SetTextI18n")
        fun bindTo(specie: Specie?) {
            this.specie = specie
            view.tv .text   = """${specie?.canonicalName}
                |${specie?.origin}""".trimMargin()
        }
    }

    class sAdpView : AnkoComponent<View> {
        lateinit var tv:TextView

        override fun createView(ui: AnkoContext<View>) =
            with(ui) {

                customView<MaterialCardView> {
                    strokeColor = ui.ctx.getColor(R.color.grey_bb)
                    elevation = dip(4).toFloat()
                    isClickable = true
                    isFocusable = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    tv =   textView {
                        minHeight = dip(64)
//                        id = SingleId.txt1
                        text = "a"
                    }
                }
            }
    }
}


