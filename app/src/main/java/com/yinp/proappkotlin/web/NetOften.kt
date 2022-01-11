package com.yinp.proappkotlin.web

import com.google.gson.JsonParseException
import com.yinp.proappkotlin.web.data.BaseRespData
import com.yinp.proappkotlin.web.data.WanAndroidCall
import com.yinp.proappkotlin.web.data.WanAndroidData
import com.yinp.proappkotlin.web.data.WanResultDispose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException
import java.util.concurrent.TimeoutException

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.web
 * describe  :
 */
const val PARSE_ERROR = 1001  //解析数据失败
const val BAD_NETWORK = 1002  //网络问题
const val CONNECT_ERROR = 1003    //连接错误
const val CONNECT_TIMEOUT = 1004  //连接超时

suspend fun <T : Any> disposeNetOuter(
    mResult: MutableStateFlow<BaseRespData<T>>,
    block: suspend () -> BaseRespData<T>
) {
    //调用函数得到结果
    var dataResult = block.invoke()
    //判断类型，并类型转换
    var result = when (dataResult) {
        is WanAndroidData<T> -> dataResult
        else -> dataResult
    }
    flow {
        emit(result)
    }.flowOn(Dispatchers.IO)
        .onStart {
        }
        .onEmpty {

        }
        .catch { e ->

        }
        .onCompletion {
        }
        .collectLatest {
            mResult.value = it
        }
}

suspend fun <T : Any> disposeNetOuter(
    mResult: MutableStateFlow<BaseRespData<T>>,
    call: WanAndroidCall?,
    block: suspend () -> BaseRespData<T>
) {
    //调用函数得到结果
    var dataResult = block.invoke()
    //判断类型，并类型转换
    var result = when (dataResult) {
        is WanAndroidData<T> -> dataResult
        else -> dataResult
    }
    flow {
        emit(result)
    }.flowOn(Dispatchers.IO)
        .onStart {
            call?.onStart()
        }
        .onEmpty {

        }
        .catch { e ->
            call?.onCatch(
                when (e.cause) {
                    is HttpException -> "网络请求异常"
                    is ConnectException, is UnknownHostException -> "网络连接异常"
                    is TimeoutException, is InterruptedIOException -> "网络超时"
                    is JsonParseException, is JSONException, is ParseException -> "数据解析异常"
                    else -> {
                        "未知异常"
                    }
                }
            )
        }
        .onCompletion {
        }
        .collectLatest {
            call?.onSuccess()
            mResult.value = it
        }
}

suspend fun <T : Any> wanDisposeNetOuter(
    mResult: MutableStateFlow<WanResultDispose<T>>,
    block: suspend () -> WanAndroidData<T>
) {
    //调用函数得到结果
    var dataResult = block.invoke()
    flow {
        emit(dataResult)
    }.flowOn(Dispatchers.IO)
        .onStart {
        }
        .onEmpty {
            mResult.value = WanResultDispose.Error("返回的数据为空")
        }
        .catch { e ->
            mResult.value = WanResultDispose.Error(
                when (e.cause) {
                    is HttpException -> "网络请求异常"
                    is ConnectException, is UnknownHostException -> "网络连接异常"
                    is TimeoutException, is InterruptedIOException -> "网络超时"
                    is JsonParseException, is JSONException, is ParseException -> "数据解析异常"
                    else -> {
                        "未知异常"
                    }
                }
            )
        }
        .collectLatest {
            when (it.errorCode) {
                0 -> mResult.value = WanResultDispose.Success(it)
                -1001 -> "未登录的"
                -1 -> mResult.value = WanResultDispose.CodeError("统一错误码", it.errorCode)
                404 -> mResult.value = WanResultDispose.CodeError("找不到访问地址", it.errorCode)
            }
        }

}