@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.yinp.proappkotlin

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import com.tencent.mmkv.MMKV
import com.yinp.proappkotlin.constant.SpConstants

/**
 * author:yinp
 * 2022/4/26+20:57
 * describe:
 */
@kotlin.internal.InlineOnly
inline fun View.visible() {
    visibility = View.VISIBLE
}

@kotlin.internal.InlineOnly
inline fun View.gone() {
    visibility = View.GONE
}

@kotlin.internal.InlineOnly
inline fun View.invisible() {
    visibility = View.INVISIBLE
}

@kotlin.internal.InlineOnly
inline fun View.circle(@ColorInt color: Int, radius: Int) {
    background = GradientDrawable().apply {
        setColor(color)
        cornerRadius = Resources.getSystem().displayMetrics.density * radius + 0.5f
    }
}


/**
 * dp转px
 * 如果赋值给有数据类型的变量，则会自动转换；如果没有，则可以自己<Int>给定
 * @return
 */
@kotlin.internal.InlineOnly
inline fun <reified T> T.dpToPx(): T {
    val scale = Resources.getSystem().displayMetrics.density
    return when (T::class) {
        Float::class -> (scale * this as Float + 0.5f) as T
        Int::class -> (scale * this as Int + 0.5f).toInt() as T
        else -> throw IllegalStateException("Type not supported")
    }
}

/**
 * px转dp
 *
 * @return
 */
@kotlin.internal.InlineOnly
inline fun <reified T> T.pxToDp(): T {
    val scale = Resources.getSystem().displayMetrics.density
    return when (T::class) {
        Float::class -> (this as Float / scale + 0.5f) as T
        Int::class -> (this as Int / scale + 0.5f).toInt() as T
        else -> throw IllegalStateException("Type not supported")
    }
}

val kv by lazy {
    MMKV.mmkvWithID(SpConstants.SP_NAME, MMKV.MULTI_PROCESS_MODE)
}

/**
 * 将值存进mmkv中
 */
@kotlin.internal.InlineOnly
inline fun <reified T> T.saveOwn(key: String) {
    when (T::class) {
        Boolean::class -> kv.encode(key, this as Boolean)
        Int::class -> kv.encode(key, this as Int)
        String::class -> kv.encode(key, this as String)
        Float::class -> kv.encode(key, this as Float)
    }
}

/**
 * 从mmkv获取值
 */
@kotlin.internal.InlineOnly
inline fun <reified T> T.getOwn(key: String): T {
    return when (T::class) {
        Boolean::class -> kv.decodeBool(key, this as Boolean) as T
        Int::class -> kv.encode(key, this as Int) as T
        String::class -> kv.encode(key, this as String) as T
        Float::class -> kv.encode(key, this as Float) as T
        else -> throw Exception("类型错误")
    }
}