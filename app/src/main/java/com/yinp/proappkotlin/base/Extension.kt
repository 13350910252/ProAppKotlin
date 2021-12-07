package com.yinp.proappkotlin.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

/**
 * @author   :yinpeng
 * date      :2021/12/3
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
inline fun <reified T : FragmentActivity> FragmentActivity.goToActivity(
    bundle: Bundle?
) {
    val intent = Intent(this, T::class.java).apply {
        bundle?.let {
            putExtras(it)
        }
    }
    startActivity(intent)
}

inline fun <reified T : FragmentActivity> FragmentActivity.goToActivity() {
    goToActivity<T>(null)
}

fun FragmentActivity.goToActivity(url: String) {
    val thiss = this
    goToActivity(Intent().apply {
        setClassName(thiss, url)
    })
}

fun FragmentActivity.goToActivity(intent: Intent) {
    startActivity(intent)
}

/**
 * 带返回的跳转页面
 */

fun FragmentActivity.goToActivityForResult(
    intent: Intent,
    arc: ActivityResultCallback<ActivityResult>
): ActivityResultLauncher<Intent> {
    return registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        arc
    ).apply {
        launch(intent)
    }
}

inline fun <reified T : FragmentActivity> FragmentActivity.goToActivityForResult(arc: ActivityResultCallback<ActivityResult>): ActivityResultLauncher<Intent> {
    return goToActivityForResult<T>(null, arc)
}

inline fun <reified T : FragmentActivity> FragmentActivity.goToActivityForResult(
    bundle: Bundle?,
    arc: ActivityResultCallback<ActivityResult>
): ActivityResultLauncher<Intent> {
    val thiss = this
    return registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        arc
    ).apply {
        launch(Intent(thiss, T::class.java).apply {
            bundle?.let {
                putExtras(bundle)
            }
        })
    }
}

/**
 * 可以写过程的
 */
fun FragmentActivity.goToActivityForResult(
    safr: ActivityResultContract<Intent, ActivityResult>,
    arc: ActivityResultCallback<ActivityResult>
): ActivityResultLauncher<Intent> {
    return registerForActivityResult(safr, arc)
}