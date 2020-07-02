package ls.yylx.lscodestore.tool


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.content.ContentResolverCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.DialogFragment
import androidx.paging.DataSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.basemodule.checkArrayPermissions
import ls.yylx.lscodestore.db.Specie
import splitties.dimensions.dip
import splitties.snackbar.snack
import splitties.toast.toast
import splitties.views.backgroundColor
import splitties.views.dsl.constraintlayout.constraintLayout
import splitties.views.dsl.constraintlayout.endToEndOf
import splitties.views.dsl.constraintlayout.lParams
import splitties.views.dsl.constraintlayout.topToTopOf
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.coordinatorlayout.defaultLParams
import splitties.views.dsl.core.*
import splitties.views.dsl.recyclerview.recyclerView
import splitties.views.imageResource
import splitties.views.padding
import splitties.views.textColorResource
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/** 使用
ChoiceImageFragment().apply {
setOnSelectedBack {

}
}.show(parentFragmentManager, null)

 **/

class ChoiceImageSplittiesFragment() : DialogFragment() {
    private var currentPhotoPath = ""

    private var public = true  //  拍照文件位置，true公共文件夹，false放到应用内部

    private var selectedBack: ((item: ChoiceImageData) -> Unit)? = null

    fun setOnSelectedBack(item: (item: ChoiceImageData) -> Unit) {
        selectedBack = item
    }

    //拍照
    fun takePicture() {
        val state = Environment.getExternalStorageState()
        if (state == Environment.MEDIA_MOUNTED) {

            val outDir =
                if (public) Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) else requireContext().getExternalFilesDir(
                    Environment.DIRECTORY_PICTURES
                )

            val sdf = SimpleDateFormat("yyyy-MM-dd-mm-ss", Locale.SIMPLIFIED_CHINESE)

            val name: String = sdf.format(Date(System.currentTimeMillis()))

            val filePhoto = File.createTempFile(
                "JPEG_${name}_", /* prefix */
                ".jpg", /* suffix */
                outDir /* directory */
            ).apply {
                currentPhotoPath = absolutePath
            }

//            // android 7.0系统解决拍照的问题
//            val builder = StrictMode.VmPolicy.Builder()
//            StrictMode.setVmPolicy(builder.build())
//            builder.detectFileUriExposure()


            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                    filePhoto.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            requireContext().packageName + ".fileProvider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, 5789)
                    }
                }
            }
        } else {
            mainUi.root.snack("没有SD卡")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 5789) {
            val file = File(currentPhotoPath)
            if (public) {

                MediaScannerConnection.scanFile(
                    requireContext(), arrayOf(file.absolutePath),
                    null
                ) { path, uri ->

                }
            }

            if (file.exists()) {
                file.let {
                    if (selectedBack != null) selectedBack!!(
                        ChoiceImageData(
                            it.toUri(),
                            it.name,
                            it.length(),
                            it.path,
                            it.lastModified()
                        )
                    )
                    dismiss()
                }
            } else {
                toast("相片不存在")
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {

            mainUi.root.snack("取消拍照")
        } else {
            mainUi.root.snack("拍照失败")
        }
    }


    val adp by lazy {
        ImagePageAdapter().apply {
            setOnItemClickListener { item, position ->
                if (position == 0) {
                    checkArrayPermissions(arrayOf(Manifest.permission.CAMERA)) {
                        if (it) {
                            takePicture()
                        } else {
                            toast("没有相机权限，无法拍照")
                        }
                    }

                } else {
                    ShowImageDialogFragment(
                        item
                    ).show(parentFragmentManager, null)
                }

            }
            setOnItemCheckedListener { item, checked, position ->

                if (lastChecked == -1) {
                    lastChecked = position
                    item.checked = true
                } else {
                    if (checked) {
                        currentList[lastChecked].checked = false
                        notifyItemChanged(lastChecked)

                        item.checked = true
                        lastChecked = position
                    } else {
                        item.checked = false
                    }
                }
            }
        }
    }


    class ChoiceUi(override val ctx: Context) : Ui {
        val ivBack = imageView {
            imageResource = R.drawable.ic_back

        }
        private val tvTitle = textView {
            text = "选择图片"
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            textColorResource = R.color.black
        }
        val tvOk = textView {
            text = "确定"
            textSize = 16f
            gravity = Gravity.CENTER

            setTextColor(Color.parseColor("#0370F4"))
        }

        private val lntTitle = horizontalLayout {
            add(ivBack, lParams(dip(48) ,dip(48) ))
            add(tvTitle, lParams(0, matchParent) {
                weight = 1f
                gravity = Gravity.CENTER_VERTICAL
            })
            add(tvOk, lParams(dip(48), dip(48)) {
                gravity = Gravity.CENTER_VERTICAL
            })
        }

        val rvMain = recyclerView {

        }

        private val lntMain = verticalLayout {
            backgroundColor = Color.parseColor("#ffffff")
            add(lntTitle, lParams(matchParent, dip(56)))
            add(rvMain, lParams(matchParent, 0) { weight = 1f })
        }

        override val root = coordinatorLayout {
            add(lntMain, defaultLParams(matchParent, matchParent))
        }
    }

    val mainUi by lazy {
        ChoiceUi(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mainUi.root


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainUi.run {
            ivBack.setOnClickListener {
                dismiss()
            }
            tvOk.setOnClickListener {
                if (adp.lastChecked == -1) {
                    root.snack("请选择一张图片")
                    return@setOnClickListener
                }
                val item = adp.currentList[adp.lastChecked]
                if (item.checked) {
                    if (selectedBack != null) selectedBack!!(item)
                    dismiss()
                } else {
                    root.snack("请选择一张图片")
                }
            }
            rvMain.run {
                layoutManager = GridLayoutManager(requireContext(), 4)
                adapter = adp
            }
        }
        initData()
    }


    fun initData() {
        checkArrayPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) { success ->
            if (success) {
                queryImage()
            } else {
                toast("没有读取sd卡权限，无法查询图片")
            }
        }
    }

    fun queryImage() {

        val imgList = mutableListOf<ChoiceImageData>()
        imgList.add(
            ChoiceImageData(
                Uri.EMPTY,
                "相机",
                0
            )
        )

        if (!public) {
            //应用内部拍照图片
            val local = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            local?.let {
                it.listFiles().forEach {
                    if (it.isFile) {
                        if (it.name.endsWith(".jpg")) {
                            imgList += ChoiceImageData(
                                it.toUri(),
                                it.name,
                                it.length(),
                                it.path,
                                it.lastModified()
                            )
                        }
                    }
                }
            }
        }


        if (isAdded) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_MODIFIED
            )

            val selection = ""
            val selectionArgs = arrayOf(
                ""
            )

            val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

            val query = ContentResolverCompat.query(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder,
                null
            )
            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val modifiedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)


                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val size = cursor.getLong(sizeColumn)
                    val path = cursor.getString(pathColumn)
                    val modified = cursor.getLong(modifiedColumn)


                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )


                    imgList += ChoiceImageData(
                        contentUri,
                        name,
                        size,
                        path,
                        modified
                    )
                }
            }


            adp.submitList(imgList.sortedByDescending { it.modified })
        }
    }
}

data class ChoiceImageData(
    val uri: Uri,
    val name: String,
    val size: Long,
    var path: String = "",
    var modified: Long = 0L,
    var checked: Boolean = false

)

val diffCallback = object : DiffUtil.ItemCallback<ChoiceImageData>() {
    override fun areItemsTheSame(oldItem: ChoiceImageData, newItem: ChoiceImageData): Boolean =
        oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: ChoiceImageData, newItem: ChoiceImageData): Boolean =
        oldItem == newItem
}


class ImagePageAdapter :
    ListAdapter<ChoiceImageData, ImagePageAdapter.ImageViewHolder>(diffCallback) {
    var lastChecked = -1

    var mHolder: ImageViewHolder? = null

    var back: ((item: ChoiceImageData, position: Int) -> Unit)? = null
    var checkBack: ((item: ChoiceImageData, checked: Boolean, position: Int) -> Unit)? = null

    fun setOnItemClickListener(item: (item: ChoiceImageData, position: Int) -> Unit) {
        back = item
    }

    fun setOnItemCheckedListener(item: (item: ChoiceImageData, checked: Boolean, position: Int) -> Unit) {
        checkBack = item
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.run {
            bindTo(item)
            mHolder = holder
            itemView.setOnClickListener {
                if (back != null) back!!(holder.image, position)
            }

            if (position == 0) {
                rvUi.cb.visibility = View.GONE
                rvUi.ivMain.run {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    padding = dip(16)
                }
                Glide.with(rvUi.ivMain).load(R.drawable.ic_camera_black_24px).into(rvUi.ivMain)
            } else {
                rvUi.cb.visibility = View.VISIBLE
                rvUi.cb.setOnCheckedChangeListener { compoundButton, b ->
                    if (checkBack != null) checkBack!!(holder.image!!, b, position)
                }
                rvUi.ivMain.run {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    padding = dip(0)
                }
                Glide.with(rvUi.ivMain).load(item.path).into(rvUi.ivMain)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val rvUi = RvChoiceUi(parent.context)

        return ImageViewHolder(rvUi)
    }


    inner class ImageViewHolder(val rvUi: RvChoiceUi) : RecyclerView.ViewHolder(rvUi.root) {
        lateinit var image: ChoiceImageData

        @SuppressLint("SetTextI18n")
        fun bindTo(item: ChoiceImageData) {
            image = item
            rvUi.cb.isChecked = item.checked
        }
    }

    class RvChoiceUi(override val ctx: Context) : Ui {
        val ivMain = imageView {
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        val cb = checkBox {
            buttonTintList = ColorStateList.valueOf(
                resources.getColor(
                    R.color.colorAccent
                )
            )
        }

        override val root = constraintLayout {
            add(ivMain, lParams(matchParent, 0) {
                dimensionRatio = "w,1:1"
                margin = dip(2)
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
            })
            add(cb, lParams(dip(32), dip(32)) {
                topToTopOf(cb)
                endToEndOf(cb)
            })
        }
    }

}

class ShowDialogUi(override val ctx: Context) : Ui {
    val ivBack = imageView {
        imageResource = R.drawable.ic_back
    }
    val tvTitle = textView {
        textSize = 16f
        typeface = Typeface.DEFAULT_BOLD
        gravity = Gravity.CENTER
        setTextColor(Color.parseColor("#000000"))
    }

    val lntTitle = horizontalLayout {
        add(ivBack, lParams(dip(48), dip(48)) {
            gravity = Gravity.CENTER_VERTICAL
        })
        add(tvTitle, lParams(matchParent, matchParent) {
            marginEnd = dip(48)
            gravity = Gravity.CENTER_VERTICAL
        })
    }

    val ivMain = imageView {
        scaleType = ImageView.ScaleType.FIT_CENTER
    }

    override val root = verticalLayout {
        setBackgroundResource(R.color.white)
        add(lntTitle, lParams(matchParent, dip(56)))
        add(ivMain, lParams(matchParent, matchParent))
    }
}

class ShowImageDialogFragment(val item: ChoiceImageData) : DialogFragment() {
    val dUi by lazy(LazyThreadSafetyMode.NONE) {
        ShowDialogUi(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = dUi.root


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dUi.run {
            ivBack.setOnClickListener { dismiss() }
            tvTitle.text = item.name
            Glide.with(requireContext()).load(item.path).into(ivMain)
        }
    }
}
