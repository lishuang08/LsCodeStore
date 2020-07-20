package ls.yylx.lscodestore.ui.rvadapter.paging

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.db.Specie
import splitties.dimensions.dip
import splitties.views.dsl.core.*
import splitties.views.dsl.material.materialCardView


val diffCallback = object : DiffUtil.ItemCallback<Specie>() {
    override fun areItemsTheSame(oldItem: Specie, newItem: Specie): Boolean =
        oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: Specie, newItem: Specie): Boolean =
        oldItem == newItem
}

class SpeciesPageAdapter : PagedListAdapter<Specie, SpeciesPageAdapter.SpecieViewHolder>(diffCallback) {

    var holder: SpecieViewHolder? = null

    var back: ((item: Specie, position: Int) -> Unit)? = null

    fun setOnItemClickListener(item: (item: Specie, position: Int) -> Unit) {
        back = item
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        val item = getItem(position)
        this.holder = holder

        item?.let {
            holder.bindTo(it )

            holder.itemView.setOnClickListener {

                back?.invoke(holder.specie, position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val rvUi = SPageUi(parent.context)

        return SpecieViewHolder(rvUi)
    }


    class SpecieViewHolder(val pui: SPageUi) : RecyclerView.ViewHolder(pui.root) {
        lateinit var specie: Specie

        @SuppressLint("SetTextI18n")
        fun bindTo(specie: Specie) {
            this.specie = specie
            pui.tv.text = """${specie.canonicalName}
                |${specie.origin}""".trimMargin()
        }
    }


    class SPageUi(override val ctx: Context) : Ui {
        val tv = textView {
            minHeight = dip(64)
            text = "a"
        }

        override val root = materialCardView {
            strokeColor = resources.getColor(R.color.grey_200)
            elevation = ctx.dip(4).toFloat()
            isClickable = true
            isFocusable = true
            lParams(matchParent, wrapContent) { }
            add(tv, lParams(matchParent, matchParent) { })
        }

    }

}


