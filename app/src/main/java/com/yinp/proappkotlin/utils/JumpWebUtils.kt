package com.yinp.proappkotlin.utils

import android.content.Context
import android.content.Intent
import com.yinp.proappkotlin.KEY_TITLE
import com.yinp.proappkotlin.KEY_URL
import com.yinp.proappkotlin.web.WebViewActivity

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.utils
 * describe  :
 */
object JumpWebUtils {
    fun startWebView(context: Context, title: String?, url: String?) {
        val intent = Intent()
        intent.setClass(context, WebViewActivity::class.java)
        intent.putExtra(KEY_TITLE, title)
        intent.putExtra(KEY_URL, url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}