package com.yinp.proappkotlin.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yinp.proappkotlin.room.bean.LocaleTaskBean
import com.yinp.proappkotlin.room.bean.UserBean
import com.yinp.proappkotlin.room.dao.LocaleTaskDao
import com.yinp.proappkotlin.room.dao.UserDao

/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.proappkotlin.room
 * describe  :
 */
@Database(version = 1, exportSchema = false, entities = [LocaleTaskBean::class, UserBean::class])
abstract class MyDataBase : RoomDatabase() {
    abstract fun localeTaskDao(): LocaleTaskDao
    abstract fun userDao(): UserDao
}