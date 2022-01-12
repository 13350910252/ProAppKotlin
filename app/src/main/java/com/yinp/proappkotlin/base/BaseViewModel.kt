package com.yinp.proappkotlin.base

import androidx.lifecycle.*
import com.yinp.proappkotlin.web.data.BaseRespData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
open class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    /**
     * 处理网络请求返回的code
     */
    fun ViewModel.dealCode(code: Int) {
        when (code) {
            404 -> ""
        }
    }
}