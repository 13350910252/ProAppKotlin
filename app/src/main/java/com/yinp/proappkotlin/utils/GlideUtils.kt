package com.yinp.proappkotlin.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.yinp.proappkotlin.R
import java.io.File

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.utils
 * describe  :
 */
object GlideUtils {
    fun into(context: Context, url: String?, iv: ImageView, error: Int) {
        try {
            val file = File(url ?: "")
            if (file.exists()) {
                Glide.with(context).load(file).error(error).placeholder(R.drawable.shape_gray_8)
                    .into(iv)
            } else {
                Glide.with(context).load(url ?: "").error(error)
                    .placeholder(R.drawable.shape_gray_8)
                    .into(iv)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun into(context: Context, url: String?, iv: ImageView) {
        try {
            Glide.with(context).load(url ?: "").into(iv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun into(context: Context, url: Int, iv: ImageView) {
        try {
            Glide.with(context).load(url).into(iv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun intoRadius(context: Context, url: String?, iv: ImageView, error: Int, radius: Int) {
        try {
            val file = File(url ?: "")
            if (file.exists()) {
                Glide.with(context).load(file)
                    .transform(CenterCrop(), GlideRoundTransform(radius))
                    .placeholder(R.drawable.shape_gray_8).error(error).into(iv)
            } else {
                Glide.with(context).load(url ?: "")
                    .transform(CenterCrop(), GlideRoundTransform(radius))
                    .placeholder(R.drawable.shape_gray_8).error(error).into(iv)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun intoRadius(context: Context, url: Int, iv: ImageView, radius: Int) {
        try {
            Glide.with(context).load(url).transform(CenterCrop(), GlideRoundTransform(radius))
                .into(iv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun intoRadius(context: Context, uri: Uri?, iv: ImageView, radius: Int, isMore: Boolean) {
        try {
            if (isMore) {
                Glide.with(context).load(uri ?: "")
                    .transform(CenterCrop(), GlideRoundTransform(radius))
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(iv)
            } else {
                Glide.with(context).load(uri).transform(CenterCrop(), GlideRoundTransform(radius))
                    .into(iv)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun intoRadius(context: Context, url: String?, iv: ImageView, radius: Int, isMore: Boolean) {
        try {
            if (isMore) {
                Glide.with(context).load(url ?: "")
                    .transform(CenterCrop(), GlideRoundTransform(radius))
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(iv)
            } else {
                Glide.with(context).load(url ?: "")
                    .transform(CenterCrop(), GlideRoundTransform(radius))
                    .into(iv)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun intoRadiusNormal(context: Context, url: String?, iv: ImageView, error: Int, radius: Int) {
        try {
            val file = File(url ?: "")
            if (file.exists()) {
                Glide.with(context).load(file).transform(GlideRoundTransform(radius))
                    .placeholder(R.drawable.shape_gray_8).error(error).into(iv)
            } else {
                Glide.with(context).load(url).transform(GlideRoundTransform(radius))
                    .placeholder(R.drawable.shape_gray_8).error(error).into(iv)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun intoRadiusNormal(context: Context, url: Int, iv: ImageView, radius: Int) {
        try {
            Glide.with(context).load(url).transform(GlideRoundTransform(radius)).into(iv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
     * isCircle和fitCenter不能同时为true，否则没有圆形
     * @param context
     * @param imageView
     * @param placeImage
     * @param url
     * @param transformation
     * @param listener
     * @param isCircle
     * @param fitCenter
     */
    fun loadUrl(
        context: Context,
        imageView: ImageView,
        placeImage: Drawable?,
        url: String?,
        transformation: Transformation<Bitmap>?,
        listener: GlideLoadListener?,
        isCircle: Boolean,
        fitCenter: Boolean
    ) {
        var options: RequestOptions
        options = if (isCircle) {
            //            options = RequestOptions.bitmapTransform(new CircleTransform());
            RequestOptions.bitmapTransform(CircleCrop())
        } else {
            RequestOptions()
        }
        options = options.format(DecodeFormat.PREFER_ARGB_8888)
        transformation?.let {
            options =
                if (imageView.scaleType == ImageView.ScaleType.CENTER_CROP)
                    options.transforms(CenterCrop(), it)
                else options.transforms(it)
        }
        if (fitCenter) options.fitCenter()
        placeImage?.let {
            options = options.error(it)
            options = options.placeholder(it)
            options = options.fallback(it)
        }
        options = options.disallowHardwareConfig()
        Glide.with(imageView).load(url).apply(options)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    listener?.failed()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    listener?.let {
                        it.success()
                        it.success(resource)
                    }
                    return false
                }
            }).into(imageView)
    }

    interface GlideLoadListener {
        fun failed()
        fun success()
        fun success(drawable: Drawable?)
    }
}