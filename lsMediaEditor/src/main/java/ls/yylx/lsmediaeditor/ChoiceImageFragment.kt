package ls.yylx.lsmediaeditor


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentResolverCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.support.v4.UI
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/** 使用
ChoiceImageFragment().apply {
setOnSelectedBack {
}
}.show(parentFragmentManager, null)

 **/

class ChoiceImageFragment() : BaseAppCompatDialogFragment() {
    var currentPhotoPath = ""

    var public = true  // 是否拍照放到应用内部


    var selectedBack: ((item: ChoiceImageData) -> Unit)? = null

    fun setOnSelectedBack(item: (item: ChoiceImageData) -> Unit) {
        selectedBack = item
    }

    //拍照
    fun takePicture() {
        val state = Environment.getExternalStorageState()
        if (state == Environment.MEDIA_MOUNTED) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

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
            snack("没有SD卡")
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
                snack("相片不存在")
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            snack("取消拍照")
        } else {
            snack("拍照失败")
        }
    }


    val adp by lazy {
        ImagePageAdapter().apply {
            setOnItemClickListener { item, position ->
                if (position == 0) {
                    checkCamera {
                        if (it) {
                            takePicture()
                        } else {
                            snack("没有相机权限，无法拍照")
                        }
                    }
                } else {
                    ShowImageDialogFragment(
                        item.name ?: "", item.path
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UI {
        verticalLayout {
            backgroundColor = Color.parseColor("#ffffff")
            linearLayout {
                imageView {
                    padding = dip(12)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    imageResource = R.drawable.ic_back
                    setOnClickListener {
                        dismiss()
                    }
                }.lparams(dip(48), dip(48)) {
                    gravity = Gravity.CENTER_VERTICAL
                }
                textView {
                    text = "选择图片"
                    textSize = 18f
                    typeface = Typeface.DEFAULT_BOLD
                    gravity = Gravity.CENTER
                    textColor = Color.parseColor("#000000")
                }.lparams(0, matchParent) {
                    weight = 1f
                    gravity = Gravity.CENTER_VERTICAL
                }
                textView {
                    text = "确定"
                    textSize = 16f
                    gravity = Gravity.CENTER
                    textColor = Color.parseColor("#0370F4")
                    setOnClickListener {
                        if (adp.lastChecked == -1) {
                            snack("请选择一张图片")
                            return@setOnClickListener
                        }
                        val item = adp.currentList[adp.lastChecked]
                        if (item.checked) {
                            if (selectedBack != null) selectedBack!!(item)
                            dismiss()
                        } else {
                            snack("请选择一张图片")
                        }

                    }
                }.lparams(dip(48), dip(48)) {
                    gravity = Gravity.CENTER_VERTICAL
                }
            }.lparams(matchParent, dip(56))

            customView<RecyclerView> {
                layoutManager = GridLayoutManager(requireContext(), 4)
                adapter = adp
            }.lparams(matchParent, 0) {
                weight = 1f
            }
        }
    }.view


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }


    fun initData() {
        checkSdPms {
            if (!it.containsValue(false)) {
                queryImage()
            } else {
                snack("没有读取sd卡权限，无法查询图片")
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
                        if (it.name.endsWith(".jpg"   ) || it.name.endsWith(".mp4")) {
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
    val name: String?,
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
    ListAdapter<ChoiceImageData, ImagePageAdapter.ImageViewHolder>(
        diffCallback
    ) {
    val ID_IMAGE = 0X0001100213
    val ID_CB = 0X0001100214
    var lastChecked = -1

    var holder: ImageViewHolder? = null

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
        holder.bindTo(item)
        this.holder = holder
        holder.itemView.setOnClickListener {
            if (back != null) back!!(holder.image!!, position)
        }

        if (position == 0) {
            holder.cb.visibility = View.GONE
            holder.iv.run {
                scaleType = ImageView.ScaleType.CENTER_CROP
                padding = dip(16)
            }
            Glide.with(holder.iv).load(R.drawable.ic_camera_black_24px).into(holder.iv)
        } else {
            holder.cb.visibility = View.VISIBLE
            holder.cb.setOnCheckedChangeListener { compoundButton, b ->
                if (checkBack != null) checkBack!!(holder.image!!, b, position)
            }
            holder.iv.run {
                scaleType = ImageView.ScaleType.CENTER_CROP
                padding = dip(0)
            }
            Glide.with(holder.iv).load(item.path).into(holder.iv)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(parent)


    inner class ImageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        MyImageRvAdapter().createView(AnkoContext.create(parent.context, parent))
    ) {
        var image: ChoiceImageData? = null
        val iv = itemView.find<ImageView>(ID_IMAGE)
        val cb = itemView.find<CheckBox>(ID_CB)


        @SuppressLint("SetTextI18n")
        fun bindTo(item: ChoiceImageData) {
            this.image = item
            cb.isChecked = item.checked

        }
    }

    inner class MyImageRvAdapter : AnkoComponent<View> {
        override fun createView(ui: AnkoContext<View>) =
            with(ui) {
                customView<ConstraintLayout> {
                    imageView {
                        id = ID_IMAGE
                        scaleType = ImageView.ScaleType.CENTER_CROP

                        layoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            0
                        ).apply {
                            dimensionRatio = "w,1:1"
                            margin = dip(2)
                            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        }
                    }
                    customView<AppCompatCheckBox> {
                        id = ID_CB
                        buttonTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.design_default_color_background))
                        layoutParams = ConstraintLayout.LayoutParams(dip(32), dip(32)).apply {
                            topToTop = ID_IMAGE
                            endToEnd = ID_IMAGE
                        }

                    }
                }
            }
    }
}



