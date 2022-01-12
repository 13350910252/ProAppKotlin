package com.yinp.proappkotlin.study.wanAndroid.data

import android.content.Context
import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


/**
 * @author   :yinpeng
 * date      :2022/1/11
 * package   :com.yinp.proappkotlin.study.wanAndroid.data
 * describe  :
 */

@JsonClass(generateAdapter = true)
data class WanLoginBean(
    @Json(name = "admin")
    val admin: Boolean = false,
    @Json(name = "chapterTops")
    val chapterTops: List<Any> = listOf(),
    @Json(name = "coinCount")
    val coinCount: Int = 0,
    @Json(name = "collectIds")
    val collectIds: List<Int> = listOf(),
    @Json(name = "email")
    val email: String = "",
    @Json(name = "icon")
    val icon: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "nickname")
    val nickname: String = "",
    @Json(name = "password")
    val password: String = "",
    @Json(name = "publicName")
    val publicName: String = "",
    @Json(name = "token")
    val token: String = "",
    @Json(name = "type")
    val type: Int = 0,
    @Json(name = "username")
    val username: String = ""
) : Serializable {
    companion object {
        @Volatile
        private var getFlag = 0

        @Volatile
        private var setFlag = 0
        var mUserInfo: WanLoginBean? = null
        fun getUserInfo(context: Context): WanLoginBean? {
            if (mUserInfo == null || getFlag != setFlag) {
                val sp = context.getSharedPreferences("WanLoginBean", Context.MODE_PRIVATE)
                val value = sp.getString("userInfo", "{}")
                getFlag = setFlag
                mUserInfo = stringToBean(value)
                return mUserInfo
            }
            return mUserInfo
        }

        fun saveUserInfo(userInfo: WanLoginBean?, context: Context) {
            if (userInfo == null) {
                return
            }
            val sp = context.getSharedPreferences("WanLoginBean", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("userInfo", jsonToString(userInfo))
            editor.apply()
            ++setFlag
        }

        fun jsonToString(userInfo: WanLoginBean?): String? {
            var value: String? = null
            try {
                val gson = Gson()
                value = gson.toJson(userInfo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return value
        }

        fun stringToBean(value: String?): WanLoginBean? {
            val gson = Gson()
            val wanLoginBean = gson.fromJson(
                value,
                WanLoginBean::class.java
            )
            try {
            } catch (e: Exception) {
                return WanLoginBean()
            }
            return wanLoginBean
        }

        fun clear(context: Context) {
            setFlag = 0
            getFlag = 0
            mUserInfo = null
            val sp = context.getSharedPreferences("WanLoginBean", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("userInfo", "{}")
            editor.apply()
        }
    }
}