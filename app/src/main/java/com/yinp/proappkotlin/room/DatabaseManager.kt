package com.yinp.proappkotlin.room

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yinp.proappkotlin.App
import com.yinp.proappkotlin.DB_NAME

/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.proappkotlin.room
 * describe  :room数据库管理类
 */
object DatabaseManager {
    private val MIGRATIONS = arrayOf(Migration1)
    val db: MyDataBase by lazy {
        Room.databaseBuilder(App.appContext, MyDataBase::class.java, DB_NAME)
            .addCallback(CreatedCallBack)
            .addMigrations(*MIGRATIONS)
            .build()
    }

    private object CreatedCallBack : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            //在新装app时会调用，调用时机为数据库build()之后，数据库升级时不调用此函数
            MIGRATIONS.map {
                Migration1.migrate(db)
            }
        }
    }

    /**
     * 可用作升级
     */
    private object Migration1 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 数据库的升级语句
            // database.execSQL("")
        }
    }
}