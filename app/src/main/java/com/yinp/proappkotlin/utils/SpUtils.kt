package com.yinp.proappkotlin.utils

import com.tencent.mmkv.MMKV
import com.yinp.proappkotlin.constant.SpConstants.SP_NAME

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
object SpUtils {
    private val kv = MMKV.mmkvWithID(SP_NAME, MMKV.MULTI_PROCESS_MODE)

    /**
     * 保存boolean常量
     */
    fun saveValue(value: Boolean, key: String?) {
        kv.encode(key, value)
    }

    /**
     * 保存boolean常量
     */
    fun getValue(key: String?, defValue: Boolean): Boolean {
        return kv.decodeBool(key, defValue)
    }

    /**
     * 保存String常量
     */
    fun saveValue(value: String?, key: String?) {
        kv.encode(key, value)
    }

    /**
     * 保存boolean常量
     */
    fun getValue(key: String?, defValue: String): String? {
        return kv.decodeString(key, defValue)
    }

    /**
     * 保存Int常量
     */
    fun saveValue(value: Int, key: String?) {
        kv.encode(key, value)
    }

    /**
     * 保存boolean常量
     */
    fun getValue(key: String?, defValue: Int = 0): Int {
        return kv.decodeInt(key, defValue)
    }
}