package com.yinp.proappkotlin.room.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.proappkotlin.room.bean
 * describe  :本地任务
 */
@Entity(tableName = "localeTaskBean")
data class LocaleTaskBean(
    @PrimaryKey(autoGenerate = true) var id: Int = 0, var isFinish: Int = 0 //任务是否完成 0false 1true
    , var date: String = ""//哪一天发布的任务
    , var time: String = ""//发布具体时间
    , var title: String = ""//可能会有标题
    , var content: String = ""//主要内容
    , var empirical: Int = 1//完成当前任务的经验值
    , var taskCycle: String = "" //任务周期day,week,month
    , var lastCycleDate: String = ""//上一次周期的任务
    , var userId: Int = -1//绑定用户的id
) {
    constructor(date: String, time: String) : this() {

    }
}