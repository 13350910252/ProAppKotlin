package com.yinp.proappkotlin.utils

import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.utils
 * describe  :
 */
class GlideRoundTransform : BitmapTransformation {
    private var radius = 0f
    private val ID = "com. bumptech.glide.transformations.FillSpace"
    private val ID_ByTES = ID.toByteArray(CHARSET)
    constructor(dp: Int) {
        radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    private fun roundCrop(pool: BitmapPool, toTransform: Bitmap?): Bitmap? {
        if (toTransform == null) {
            return null
        }
        val result = pool[toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(result)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rectF = RectF(
            0f, 0f, toTransform.width.toFloat(),
            toTransform.height.toFloat()
        )
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }


    override fun equals(o: Any?): Boolean {
        if (o is GlideRoundTransform) {
            return radius == o.radius
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(
            ID.hashCode(),
            Util.hashCode(radius)
        )
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_ByTES)
        val radiusData = ByteBuffer.allocate(4).putInt(radius.toInt()).array()
        messageDigest.update(radiusData)
    }
}