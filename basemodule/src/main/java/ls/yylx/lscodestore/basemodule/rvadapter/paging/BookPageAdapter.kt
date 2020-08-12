package ls.yylx.lscodestore.basemodule.rvadapter.paging


//class BookPageAdapter : PagedListAdapter<BookItem, BookPageAdapter.BookViewHolder>(diffCallback) {
//
//    var holder: BookViewHolder? = null
//
//    lateinit var back: (item: BookItem, position: Int) -> Unit
//
//
//    fun setOnItemClickListener(item: (item: BookItem, position: Int) -> Unit) {
//        back = item
//    }
//
//    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
//        holder.bindTo(getItem(position))
//        this.holder = holder
//        holder.itemView.setOnClickListener {
//            back(holder.book!!, position)
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder = BookViewHolder(parent)
//
//    companion object {
//
//        private val diffCallback = object : DiffUtil.ItemCallback<BookItem>() {
//            override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
//                oldItem.id == newItem.id
//
//            override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
//                oldItem == newItem
//        }
//    }
//
//
//    inner class BookViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
////            adpView().createView(AnkoContext.create(parent.context, parent))
////            inflate(parent.context, R.item_book.item_loacl_book, null)
//            LayoutInflater.from(parent.context).inflate(R.item_book.item_loacl_book, null, false)
//
//    ) {
//        var book: BookItem? = null
//        val name = itemView.find<TextView>(R.id.tv_local_book)
//
////        val author = itemView.find<TextView>(txt1)
//
//        @SuppressLint("SetTextI18n")
//        fun bindTo(book: BookItem?) {
//            this.book = book
//            name.text = """${book?.volumeInfo?.title}
//                |${book?.volumeInfo?.authors}""".trimMargin()
//        }
//    }
//
////    inner class adpView : AnkoComponent<View> {
////        override fun createView(ui: AnkoContext<View>) =
////            with(ui) {
////                include<MaterialCardView>(R.item_book.md_card) {
////                    backgroundResource = attr(R.attr.selectableItemBackground).resourceId
////
////                    textView {
////                        minHeight = dip(64)
////                        id = SingleId.txt0
////                        text = "a"
////                    }
////                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
////
////                }
////            }
////    }
//}
//
//
//
//
