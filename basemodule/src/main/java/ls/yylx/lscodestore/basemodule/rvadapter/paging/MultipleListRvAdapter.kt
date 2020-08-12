package ls.yylx.lscodestore.basemodule.rvadapter.paging

import android.annotation.SuppressLint
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.basemodule.R
import org.jetbrains.anko.*

//private val diffCallback = object : DiffUtil.ItemCallback<MainItem>() {
//    override fun areItemsTheSame(oldItem: MainItem, newItem: MainItem): Boolean =
//        oldItem == newItem
//
//    override fun areContentsTheSame(oldItem: MainItem, newItem: MainItem): Boolean =
//        oldItem === newItem
//}
class mainItemDiff : DiffUtil.ItemCallback<MainItem>() {
    override fun areItemsTheSame(oldItem: MainItem, newItem: MainItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: MainItem, newItem: MainItem): Boolean =
        oldItem === newItem
}

/**
 **@author ls
 * 2018/8/15
 *  基于dsl的  多种布局的adapter，如果需要使用未知布局的include，则最好整体使用xml
 *  anko的dsl如果使用include，还是会解析一次xml，然后再加载布局，效率低于xml直接使用
 **/
class MultipleListRvAdapter : ListAdapter<MainItem, RecyclerView.ViewHolder>(mainItemDiff()) {


    var back: ((item: MainItem, position: Int) -> Unit)? = null

//    /**  val newList= items.toMutableList()或者 val newList = items.toList() ,来复制list的值,
//     * list地址改变，但是item不会改变 ，
//     * val newList  = items.map { it.copy() },会修改list地址和item的地址
//     *
//     * 只有整个list变化的时候才会触发
//     * **/
//    var items: List<MainItem> by Delegates.observable(emptyList()) { _, old, new ->
//        autoNotify(old, new) { o, n -> o === n }
//    }


//    override fun getItemCount() = items.size


    fun setOnItemClickListener(item: (item: MainItem, position: Int) -> Unit) {
        back = item
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.let {
            when (it) {
                is FirstViewHolder -> {
                    it.bindTo(getItem(position) as ConvenientServiceItem)
                }
                is SecondViewHolder -> {
                    it.bindTo(getItem(position) as HotServiceItem)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ConvenientServiceItem -> {
                0
            }
            is HotServiceItem -> {
                1
            }
            is CityInfoItem -> {
                2
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> FirstViewHolder(parent)
        else -> SecondViewHolder(parent)
    }


    inner class FirstViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        viewType0().createView(AnkoContext.create(parent.context, parent))
    ) {
//        val name = itemView.find<TextView>(SingleId.txt0)

//        val author = itemView.find<TextView>(txt1)

        fun bindTo(item: ConvenientServiceItem?) {
            Logger.e("item  ConvenientServiceItem ")

        }
    }


    inner class SecondViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        viewType1().createView(AnkoContext.create(parent.context, parent))
    ) {
//        val name = itemView.find<TextView>(SingleId.txt0)


        @SuppressLint("SetTextI18n")
        fun bindTo(item: HotServiceItem?) {
            Logger.e("item  HotServiceItem ")

        }
    }


    inner class viewType0 : AnkoComponent<View> {
        override fun createView(ui: AnkoContext<View>) =
            with(ui) {

                linearLayout {

                    gravity = Gravity.CENTER
                    orientation = LinearLayout.VERTICAL
                    imageView {
//                        imageResource = R.drawable.ic_cached_black_24dp
                        //app:srcCompat = @color/colorAccent //not support attribute
                    }.lparams(width = dip(48), height = dip(48))
                    textView {
                        gravity = Gravity.CENTER
                        text = "111"
                    }.lparams(width = matchParent)


                }

//                include<MaterialCardView>(R.item_book.md_card) {
//                    textView {
//                        minHeight = dip(64)
//                        id = SingleId.txt0
//                        text = "a"
//                    }
//
//                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//                }
            }
    }

    inner class viewType1 : AnkoComponent<View> {
        override fun createView(ui: AnkoContext<View>) =
            with(ui) {

                linearLayout {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground =
                            context.getDrawable(attr(R.attr.selectableItemBackground).resourceId)
                    }
                    gravity = Gravity.CENTER
                    orientation = LinearLayout.VERTICAL
                    imageView {
//                        id = ivId
                        //app:srcCompat = @color/colorAccent //not support attribute
                    }.lparams(width = dip(48), height = dip(48))
                    textView {
//                        id = SingleId.txt0
                        gravity = Gravity.CENTER
                        text = "111"
                    }.lparams(width = matchParent)
                }

//                include<MaterialCardView>(R.item_book.md_card) {
//                    textView {
//                        minHeight = dip(64)
//                        id = SingleId.txt0
//                        text = "a"
//                    }
//
//                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//                }
            }
    }


}

sealed class MainItem


@Entity

data class ConvenientServiceItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var icon: Int = 0,
    var type: Int = 0,//0:我的应用    1：社保查询    2：纳税信息    3：医疗卫生
    var order: Int = 0,

    @TypeConverters(RoomCsConverters::class) var list: List<CsItem> = emptyList()


) : MainItem()

data class CsItem(
    var id: Long = 0,
    var name: String = "",
    var url: String = ""
)

class RoomCsConverters {
    val gson = Gson()
    val listType = object : TypeToken<List<CsItem>>() {}.type

    @TypeConverter
    fun fromRoomToValue(dbString: String): List<CsItem>? {
        return gson.fromJson(dbString, listType)
    }

    @TypeConverter
    fun fromValueToRoom(date: List<CsItem>): String {
        return gson.toJson(date)
    }
}


@Entity
data class HotServiceItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var icon: Int = 0,
    var type: Int = 0,//0:我的应用    1：社保查询    2：纳税信息    3：医疗卫生
    var order: Int = 0
) : MainItem()


@Entity
data class CityInfoItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var icon: Int = 0,
    var type: Int = 0,//0:我的应用    1：社保查询    2：纳税信息    3：医疗卫生
    var order: Int = 0
) : MainItem()


