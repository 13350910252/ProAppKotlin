package com.yinp.proappkotlin.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.yinp.proappkotlin.pageSize
import com.yinp.proappkotlin.room.bean.LocaleTaskBean
import com.yinp.tools.utils.DateUtils.CurData
import kotlinx.coroutines.flow.Flow

/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.proappkotlin.room.dao
 * describe  :
 */
@Dao
interface LocaleTaskDao : BaseDao<LocaleTaskBean> {
    //select time from Mylo2g order by time desc limit 1
    fun queryCurDateNoFinishList(page: Int): Flow<List<LocaleTaskBean>> {
        return queryCurDateNoFinishList(page, CurData.curDate(), pageSize)
    }

    @Query("select * from localeTaskBean where isFinish = 0 and date = :date limit :page*:size,:size")
    fun queryCurDateNoFinishList(
        page: Int,
        date: String,
        size: Int
    ): Flow<List<LocaleTaskBean>>
}