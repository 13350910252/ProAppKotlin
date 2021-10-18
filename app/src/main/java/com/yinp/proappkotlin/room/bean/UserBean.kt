package com.yinp.proappkotlin.room.bean

import androidx.annotation.IntRange
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.proappkotlin.room.bean
 * describe  :
 */
@Entity(tableName = "userBean")
data class UserBean(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var userName: String = "",
    var password: String = "",
    @IntRange(from = 0, to = 2) var sex: Int = 0,//0啥也不是1男2女
    var nickName: String = "",
    var headUrl: String = ""
) {
    constructor(userName: String, password: String) : this() {

    }

    constructor(userName: String, password: String, nickName: String, headUrl: String) : this() {

    }
}