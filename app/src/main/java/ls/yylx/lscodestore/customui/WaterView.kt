package ls.yylx.lscodestore.customui

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.orhanobut.logger.Logger

class WaterView(ctx: Context, attributeSet: AttributeSet?) : View(ctx, attributeSet) {


    private var waterPaths = arrayListOf<Path>()

    private var bufferBmp: Bitmap? = null
    private var bufferCanvas: Canvas? = null

    private var waterText = ""
    private var mWidth = 0
    private var mHeight = 0

    private var tTop = 80f//水印高度差
    private var tSize = 80f //文字大小

    private var tLength = 0

    val paint = TextPaint().apply {
        style = Paint.Style.FILL
        strokeWidth = 4f
        textSize = tSize
        color = Color.BLACK
        isAntiAlias = true
        alpha = 50
    }


    fun setNewText(str: String) {
        waterText = str
        tLength = waterText.length

        if (bufferBmp == null) {
            bufferBmp = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
            bufferCanvas = Canvas(bufferBmp!!)
        }

        val ww = tLength * tSize//水印每个宽度
//        val wh = tSize + tTop //水印每个高度

        val wStep = ww / 1.415f

        val wCount = (mWidth / wStep  ).toInt() + 1
        val hCount = (mHeight / wStep).toInt() + 1

        (0..hCount).forEach { h ->
            (0..wCount).forEach { w ->
                val xStart = wStep * (w + -(h % 2) / 2f)
                val yStart = wStep * h

                val xEnd = xStart + wStep
                val yEnd = yStart - wStep

                val path = Path().apply {
                    moveTo(xStart, yStart)
                    lineTo(xEnd, yEnd)
                }

                waterPaths.add(path)

                Logger.e("$xStart  to  $xEnd                 $yStart  to  $yEnd")
            }
        }

        bufferCanvas?.run {
            waterPaths.forEach {
                drawTextOnPath(waterText, it, 20f, 20f, paint)
            }
        }

        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        canvas?.drawBitmap(bufferBmp!!, 0f, 0f, null)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)


//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(mWidth, mHeight)
//        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(mWidth, mHeight)
//        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(mWidth, mHeight)
//        }

        setMeasuredDimension(mWidth, mHeight)
    }

}