package com.yinp.proappkotlin.web

import android.util.Log
import com.google.gson.JsonParseException
import com.yinp.proappkotlin.WanAndroid.NET_CATCH
import com.yinp.proappkotlin.WanAndroid.NET_COMPLETION
import com.yinp.proappkotlin.WanAndroid.NET_EMPTY
import com.yinp.proappkotlin.WanAndroid.NET_START
import com.yinp.proappkotlin.web.data.BaseRespData
import com.yinp.proappkotlin.web.data.WanAndroidData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
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
    channel: Channel<WanAndroidData<*>>,
    type: String,
    block: suspend () -> BaseRespData<T>
) {
    val wanAndroidData = WanAndroidData<T>()
    wanAndroidData.eventType = type

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
            wanAndroidData.eventCode = NET_START
            channel.send(wanAndroidData)
        }
        .onEmpty {
            wanAndroidData.eventCode = NET_EMPTY
            channel.send(wanAndroidData)
        }
        .catch {
            wanAndroidData.exContent = when (it.cause) {
                is HttpException -> "网络请求异常"
                is ConnectException, is UnknownHostException -> "网络连接异常"
                is TimeoutException, is InterruptedIOException -> "网络超时"
                is JsonParseException, is JSONException, is ParseException -> "数据解析异常"
                else -> {
                    "未知异常"
                }
            }
            wanAndroidData.eventCode = NET_CATCH
            wanAndroidData.success = false
            channel.send(wanAndroidData)
        }
        .onCompletion {
            wanAndroidData.eventCode = NET_COMPLETION
            channel.send(wanAndroidData)
        }
        .collect {
            wanAndroidData.success = true
            mResult.value = it
        }
}

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
            Log.i("netState:-------------:", "开始请求")
        }
        .onEmpty {
            Log.i("netState:-------------:", "数据为空")

        }
        .catch { e ->
            Log.i("netState:-------------:", "错误：${e.message}")

        }
        .onCompletion {
            Log.i("netState:-------------:", "请求完成")
        }
        .collectLatest {
            Log.i("netState:-------------:", "aaa")
            mResult.value = it
        }
}

//suspend fun <T : Any> disposeNet(
//    mResult: MutableStateFlow<BaseRespData<T>>,
//    block: suspend () -> T
//) {
//    var blockResult: T
//    var result = BaseRespData<T>()
//
//    flow {
//        blockResult = block.invoke()
//        result.values = blockResult
//        emit(result)
//    }.flowOn(Dispatchers.IO)
//        .onStart {
//            Log.i("netState:-------------:", "开始请求")
////            mResult.value =
//        }
//        .onEmpty {
//            Log.i("netState:-------------:", "数据为空")
//
//        }
//        .catch { e ->
//            Log.i("netState:-------------:", "错误：${e.message}")
//
//        }
//        .onCompletion {
//            Log.i("netState:-------------:", "请求完成")
//        }
//        .collect {
//            Log.i("netState:-------------:", "aaa")
//            mResult.value = result
//        }
//}