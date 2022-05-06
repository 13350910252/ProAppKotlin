@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")
package com.yinp.tools

import android.content.res.Resources

/**
 * author:yinp
 * 2022/5/6+21:32
 * describe:
 */
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