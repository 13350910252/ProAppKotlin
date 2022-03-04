package com.yinp.proappkotlin.base

/**
 * @author   :yinpeng
 * date      :2021/12/3
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
// 使用扩展函数重写 contains 操作符
operator fun Regex.contains(text: CharSequence): Boolean {
    return this.containsMatchIn(text)
}

/**
 */
typealias IMsa = IManageStartActivity

/**
 */
fun msa(): SkipActivity = SkipActivity()
//=======================================================================================
