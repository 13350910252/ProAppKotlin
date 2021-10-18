package com.yinp.proappkotlin.room.dao

import androidx.room.Dao
import com.yinp.proappkotlin.room.bean.UserBean

/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.proappkotlin.room.dao
 * describe  :
 */
@Dao
interface UserDao : BaseDao<UserBean> {
}