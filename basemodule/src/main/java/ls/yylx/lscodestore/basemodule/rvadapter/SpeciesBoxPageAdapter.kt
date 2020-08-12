package ls.yylx.lscodestore.basemodule.rvadapter

//class SpecieBoxsBoxPageAdapter(ctx: Context) : PagingDataAdapter<Specie, SpecieBoxsBoxPageAdapter.SpecieViewHolder>(diffCallback) {
//
//    val width by lazy {
//        ctx.resources.displayMetrics.widthPixels
//    }
//
//
//    var back: ((item: Specie, position: Int, shareIv: ImageView, shareTv: TextView) -> Unit)? = null
//
//
//    fun setOnItemClickListener(item: (item: Specie, position: Int, shareIv: ImageView, shareTv: TextView) -> Unit) {
//        back = item
//    }
//
//    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
//
//        holder.bindTo(getItem(position))
//        holder.itemView.setOnClickListener {
//            if (back != null) back!!(holder.specie!!, position, holder.iv, holder.tv)
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder = SpecieViewHolder(parent)
//
//
//    companion object {
//
//        private val diffCallback = object : DiffUtil.ItemCallback<Specie>() {
//            override fun areItemsTheSame(oldItem: Specie, newItem: Specie): Boolean =
//                    oldItem.key == newItem.key
//
//            override fun areContentsTheSame(oldItem: Specie, newItem: Specie): Boolean =
//                    oldItem == newItem
//        }
//
//
//    }
//
//    var params: PrecomputedTextCompat.Params? = null
//
//
//    inner class SpecieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
//            sAdpView().createView(AnkoContext.create(parent.context, parent))) {
//        var specie: Specie? = null
//
//
//
//        val params by lazy {
//            TextViewCompat.getTextMetricsParams(tv)
//        }
//
//
//        @SuppressLint("SetTextI18n")
//        fun bindTo(specie: Specie?) {
//            this.specie = specie
//
//
////            println("params    $params")
//            val precomputedText =
//                    PrecomputedTextCompat.create("""
//                |${specie?.key}
//                |${specie?.nameKey}
//                |${specie?.canonicalName}
//                |${specie?.origin}""".trimMargin(), params)
//
//
////            val a = PrecomputedTextCompat.getTextFuture(
////                    """
////                |${specie?.key}
////                |${specie?.nameKey}
////                |${specie?.canonicalName}
////                |${specie?.origin}""".trimMargin(),
////                    params,
////                    null).get()
//
//            TextViewCompat.setPrecomputedText(tv, precomputedText)
//
//            val moveInIv = ObjectAnimator.ofFloat(iv, "translationX", -width / 2F, 0F).apply {
//                duration = 300
//            }
//            val moveInTv = ObjectAnimator.ofFloat(tv, "translationX", -width / 2F, 0F).apply {
//                duration = 300
//            }
//
//            moveInTv.start()
//            moveInIv.start()
//        }
//    }
//
//
//    class sAdpView : AnkoComponent<View> {
//        override fun createView(ui: AnkoContext<View>) =
//                with(ui) {
//
//                    customView<MaterialCardView> {
////                        strokeColor = ui.ctx.getColor(R.color.grey_bb)
//                        elevation = dip(4).toFloat()
//                        isClickable = true
//                        isFocusable = true
//                        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//
//                    }
//                }
//    }
//
//
//}


