package com.yinp.tools

import android.view.Gravity
import androidx.annotation.IntDef

/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools
 * describe  :
 */
/**
 * shap_view系列
 */
const val DEFAULT = 0
const val PRESSED = 1
const val SELECTED = 2
const val CHECKED = 3
const val FOCUSED = 4

@IntDef(value = [DEFAULT, PRESSED, SELECTED, CHECKED, FOCUSED])
@Retention(AnnotationRetention.SOURCE)
annotation class AndroidState

const val state_pressed = android.R.attr.state_pressed
const val state_selected = android.R.attr.state_selected
const val state_checked = android.R.attr.state_checked
const val state_focused = android.R.attr.state_focused
const val state_enabled = android.R.attr.state_enabled

val MNONE = LazyThreadSafetyMode.NONE
