package com.yinp.proappkotlin.recreation.game.fourcolormap

import android.graphics.Color

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.recreation
 * describe  :
 */
object FCM_judge {
    /**
     * @param provinceItemList
     * @param position
     * @param curColor
     * @return 返回false表示无相邻
     */
    fun judge(provinceItemList: List<ProvinceItem>, position: Int, curColor: Int): Boolean {
        if (curColor == Color.WHITE) {
            return false
        }
        when (position) {
            30 -> return isThem(curColor, provinceItemList, 31, 22, 5)
            31 -> return isThem(curColor, provinceItemList, 30, 22, 24, 32)
            22 -> return isThem(curColor, provinceItemList, 30, 31, 5, 24)
            5 -> return isThem(curColor, provinceItemList, 30, 22, 24, 20, 21, 23)
            24 -> return isThem(curColor, provinceItemList, 31, 22, 5, 23, 2, 32, 7)
            32 -> return isThem(curColor, provinceItemList, 31, 24, 7, 6)
            20 -> return isThem(curColor, provinceItemList, 5, 21, 23, 27, 9, 18, 15, 12)
            21 -> return isThem(curColor, provinceItemList, 20, 5, 23)
            23 -> return isThem(curColor, provinceItemList, 21, 5, 2, 24, 20, 27, 10, 14)
            2 -> return isThem(curColor, provinceItemList, 23, 24, 7, 14, 13)
            7 -> return isThem(curColor, provinceItemList, 2, 24, 32, 13, 6)
            6 -> return isThem(curColor, provinceItemList, 32, 7, 13, 4)
            27 -> return isThem(curColor, provinceItemList, 20, 23, 10, 9)
            9 -> return isThem(curColor, provinceItemList, 27, 20, 10, 18, 25, 28, 1)
            18 -> return isThem(curColor, provinceItemList, 9, 15, 20)
            15 -> return isThem(curColor, provinceItemList, 12, 18, 20)
            12 -> return isThem(curColor, provinceItemList, 15, 20)
            10 -> return isThem(curColor, provinceItemList, 9, 27, 23, 14, 25, 0)
            14 -> return isThem(curColor, provinceItemList, 2, 23, 10, 13, 0, 17)
            13 -> return isThem(curColor, provinceItemList, 14, 2, 7, 4, 17, 6)
            4 -> return isThem(curColor, provinceItemList, 6, 13, 17, 3, 8)
            25 -> return isThem(curColor, provinceItemList, 10, 9, 0, 16)
            28 -> return isThem(curColor, provinceItemList, 1, 9)
            1 -> return isThem(curColor, provinceItemList, 28, 9)
            17 -> return isThem(curColor, provinceItemList, 0, 13, 14, 33, 4, 3)
            0 -> return isThem(curColor, provinceItemList, 14, 10, 25, 33, 17, 16)
            16 -> return isThem(curColor, provinceItemList, 25, 0, 33, 26)
            3 -> return isThem(curColor, provinceItemList, 33, 17, 4)
            33 -> return isThem(curColor, provinceItemList, 3, 26, 17, 0, 16)
            26 -> return isThem(curColor, provinceItemList, 33, 16)
            8 -> return isThem(curColor, provinceItemList, 4)
            29 -> return false
            11 -> return false
            19 -> return false
        }
        return false
    }

    private fun isThem(
        curColor: Int,
        provinceItemList: List<ProvinceItem>,
        vararg positions: Int
    ): Boolean {
        for (element in positions) {
            if (provinceItemList[element].selectColor == curColor) {
                return true
            }
        }
        return false
    }
}