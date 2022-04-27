@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")
package com.yinp.proappkotlin

import android.view.View
import kotlin.internal.InlineOnly

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
