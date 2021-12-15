package com.yinp.tools.utils

import android.annotation.SuppressLint
import android.util.Log
import java.net.URL
import java.net.URLConnection
import java.text.DateFormat
import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.tools.utils
 * describe  :
 */
object DateUtils {
    val Y_M_D_H_M_S_S: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SS", Locale.getDefault())
        }
    }
    val yyyy_MM_dd: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        }
    }
    val yyyy_MM_dd_HH_mm: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            }
        }

    /**
     * 格式日期
     *
     * @param time
     * @return
     */
    fun getFormatDate(time: Long): String? {
        return try {
            val format = "yyyy-MM-dd HH:mm"
            @SuppressLint("SimpleDateFormat") val format1 = SimpleDateFormat(format)
            val date = Date(time)
            format1.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 得到本机时间并转换成 年-月-日 时:分:秒 24小时制
     *
     * @return
     */
    fun getFormatDate(date: Date?): String? {
        @SuppressLint("SimpleDateFormat") val format: Format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    fun getFormatDate(time: String?): String? {
        return try {
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
            time?.let {
                format.parse(it)?.let { it2 ->
                    format.format(it2)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 转化格林尼治时间
     */
    fun getDateFormat(oldDateStr: String?): String? {
        return try {
            //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX

            //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            @SuppressLint("SimpleDateFormat") val df: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            var date: Date? = null
            date = df.parse(oldDateStr)
            @SuppressLint("SimpleDateFormat") val df2: DateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm")
            df2.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 转化格林尼治时间
     */
    fun getDateFormat1(oldDateStr: String?): String? {
        return try {
            //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX

            //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            @SuppressLint("SimpleDateFormat") val df: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            var date: Date? = null
            date = df.parse(oldDateStr)
            @SuppressLint("SimpleDateFormat") val df2: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            df2.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }


    fun getTime(user_time: String?): String? {
        var re_time: String? = null
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val d: Date
        try {
            d = sdf.parse(user_time)
            val l: Long = d.time
            val str = l.toString()
            re_time = str.substring(0, 10)
        } catch (e: ParseException) {
// TODO Auto-generated catch block
            e.printStackTrace()
        }
        return re_time
    }

    /*
     * 指定时间加小时
     * */
    fun addDate(day: String?, x: Int): String? {
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("yyyy-MM-dd HH:mm") //24小时制
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
        var date: Date? = null
        try {
            date = format.parse(day)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        if (date == null) return ""
        var cal: Calendar = Calendar.getInstance()
        cal.setTime(date)
        cal.add(Calendar.HOUR_OF_DAY, x) //24小时制
        //cal.add(Calendar.HOUR, x);12小时制
        date = cal.getTime()
        println("front:$date")
        return format.format(date)
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    fun compareNowTime(time: String?, nowTime: String?): Boolean {
        var isDayu = false
        @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val parse: Date = dateFormat.parse(time)
            //获取当前时间
            val parse1: Date = dateFormat.parse(nowTime)
            val diff: Long = parse1.time - parse.time
            isDayu = diff <= 0
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return isDayu
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * 返回类型 ==1----天，时，分。 ==2----时
     * @return 返回时间差
     */
    fun getTimeDifference(starTime: String?, endTime: String?): String? {
        var timeString = ""
        @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val parse: Date = dateFormat.parse(starTime)
            val parse1: Date = dateFormat.parse(endTime)
            val diff: Long = parse1.time - parse.time
            val day = diff / (24 * 60 * 60 * 1000)
            val hour = diff / (60 * 60 * 1000) - day * 24
            val min = diff / (60 * 1000) - day * 24 * 60 - hour * 60
            val s = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
            val m = diff / (60 * 1000)
            timeString = m.toString()
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return timeString
    }

    fun getNetTime(): String {
        Thread {
            val webUrl = "http://www.baidu.com" //中国科学院国家授时中心
            try {
                val url = URL(webUrl)
                val uc: URLConnection = url.openConnection()
                uc.setReadTimeout(5000)
                uc.setConnectTimeout(5000)
                uc.connect()
                val correctTime: Long = uc.getDate()
                @SuppressLint("SimpleDateFormat") val formatter: DateFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val calendar: Calendar = Calendar.getInstance()
                calendar.timeInMillis = correctTime
                val format: String = formatter.format(calendar.getTime())
                Log.e("time", format)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
        return ""
    }

    fun getSystemTime(): String {
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str: String = formatter.format(curDate)
        Log.e("sysTime", str)
        return str
    }

    fun getNowTimeHour(): String {
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat("HH")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str: String = formatter.format(curDate)
        Log.e("sysTime", str)
        return str
    }

    fun getNowTimeMinutes(): String {
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat("mm")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str: String = formatter.format(curDate)
        Log.e("sysTime", str)
        return str
    }

    val yyyy_MM_dd_hhmmss: ThreadLocal<SimpleDateFormat?> =
        object : ThreadLocal<SimpleDateFormat?>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            }
        }
    val yyyyMMdd: ThreadLocal<SimpleDateFormat?> = object : ThreadLocal<SimpleDateFormat?>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA)
        }
    }

    /**
     * 将字符串转化为对应格式的日期
     *
     * @param format
     * @param dateStr
     * @return
     * @description
     * @date 2014-7-22
     * @author zuolong
     */
    fun toDate(
        format: ThreadLocal<SimpleDateFormat>,
        dateStr: String?
    ): Date? {
        return try {
            format.get().parse(dateStr)
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * 以下是计算时间差值，返回值比如1秒前，5分钟前等等
     */
    private const val ONE_MINUTE = 60000L
    private const val ONE_HOUR = 3600000L
    private const val ONE_DAY = 86400000L
    private const val ONE_WEEK = 604800000L

    private const val ONE_SECOND_AGO = "秒前"
    private const val ONE_MINUTE_AGO = "分钟前"
    private const val ONE_HOUR_AGO = "小时前"
    private const val ONE_DAY_AGO = "天前"
    private const val ONE_MONTH_AGO = "月前"
    private const val ONE_YEAR_AGO = "年前"

    fun format(date: Date): String {
        val delta = Date().time - date.time
        if (delta < 1L * ONE_MINUTE) {
            val seconds = toSeconds(delta)
            return (if (seconds <= 0) 1 else seconds).toString() + ONE_SECOND_AGO
        }
        if (delta < 45L * ONE_MINUTE) {
            val minutes = toMinutes(delta)
            return (if (minutes <= 0) 1 else minutes).toString() + ONE_MINUTE_AGO
        }
        if (delta < 24L * ONE_HOUR) {
            val hours = toHours(delta)
            return (if (hours <= 0) 1 else hours).toString() + ONE_HOUR_AGO
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天"
        }
        if (delta < 30L * ONE_DAY) {
            val days = toDays(delta)
            return (if (days <= 0) 1 else days).toString() + ONE_DAY_AGO
        }
        return if (delta < 12L * 4L * ONE_WEEK) {
            val months = toMonths(delta)
            (if (months <= 0) 1 else months).toString() + ONE_MONTH_AGO
        } else {
            val years = toYears(delta)
            (if (years <= 0) 1 else years).toString() + ONE_YEAR_AGO
        }
    }

    private fun toSeconds(date: Long): Long {
        return date / 1000L
    }

    private fun toMinutes(date: Long): Long {
        return toSeconds(date) / 60L
    }

    private fun toHours(date: Long): Long {
        return toMinutes(date) / 60L
    }

    private fun toDays(date: Long): Long {
        return toHours(date) / 24L
    }

    private fun toMonths(date: Long): Long {
        return toDays(date) / 30L
    }

    private fun toYears(date: Long): Long {
        return toMonths(date) / 365L
    }

    /**
     * 获取对应格式的日期字符串
     *
     * @param format
     * @param date
     * @return
     * @description
     * @date 2014-7-22
     * @author zuolong
     */
    fun toDateString(
        format: ThreadLocal<SimpleDateFormat>,
        date: Any?
    ): String? {
        return try {
            format.get().format(date)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取当前时间对应格式的日期字符串
     *
     * @param format 日期格式
     * @return 当前格式日期时间
     */
    fun toDateString(format: ThreadLocal<SimpleDateFormat>): String? {
        return format.get().format(System.currentTimeMillis())
    }

    /**
     * 1990-12-31T16:00:00.000+0000
     *
     * @param oldDate
     * @return
     */
    fun dealDateFormat(oldDate: String?): String? {
        var date1: Date? = null
        var df2: DateFormat? = null
        try {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date: Date = df.parse(oldDate)
            val df1 = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK)
            date1 = df1.parse(date.toString())
            df2 = SimpleDateFormat("yyyy年MM月dd号")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return df2?.format(date1)
    }

    /**
     * 获取指定年月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    fun getFirstDayOfMonth1(year: Int, month: Int): String? {
        val cal = Calendar.getInstance()
        //设置年份
        cal.set(Calendar.YEAR, year)
        //设置月份
        cal.set(Calendar.MONTH, month - 1)
        //获取某月最小天数
        val firstDay = cal.getMinimum(Calendar.DATE)
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay)
        //格式化日期
        return SimpleDateFormat("yyyy-MM-dd").format(cal.time)
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    fun getLastDayOfMonth1(year: Int, month: Int): String? {
        val cal = Calendar.getInstance()
        //设置年份
        cal.set(Calendar.YEAR, year)
        //设置月份
        cal.set(Calendar.MONTH, month - 1)
        //获取某月最大天数
        val lastDay = cal.getActualMaximum(Calendar.DATE)
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay)
        //格式化日期
        return SimpleDateFormat("yyyy-MM-dd").format(cal.time)
    }

    object scrollDate {
        const val DEFAULT_START_YEAR = 1900
        var hourList = mutableListOf(
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
        )

        /**
         * 默认是1990到当前年
         *
         * @return
         */
        fun setYear(): List<String> {
            return setYear(DEFAULT_START_YEAR)
        }

        fun setYear(startYear: Int): List<String> {
            val calendar = Calendar.getInstance()
            val curYear = calendar.get(Calendar.YEAR)
            return setYear(startYear, curYear)
        }

        fun setYear(startYear: Int, endYear: Int): List<String> {
            var startYear = if (startYear < DEFAULT_START_YEAR) {
                DEFAULT_START_YEAR
            } else {
                startYear
            }
            val list = mutableListOf<String>()
            if (startYear > endYear) {
                for (i in startYear downTo endYear) {
                    list.add(i.toString())
                }
            } else {
                for (i in startYear..endYear) {
                    list.add(i.toString())
                }
            }
            return list
        }

        /**
         * 默认1到12月
         *
         * @return
         */
        fun setMonth(): List<String>? {
            return setMonth(1, 12)
        }

        fun setMonth(startMonth: Int, endMonth: Int): List<String>? {
            if (startMonth > endMonth || startMonth < 1 || endMonth > 12) {
                return null
            }
            val list = mutableListOf<String>()
            for (i in startMonth..endMonth) {
                list.add(i.toString())
            }
            return list
        }

        /**
         * 日期会随着年和月的改变而改变
         *
         * @param year
         * @param month
         * @return
         */
        fun setDay(year: Int, month: Int): List<String> {
            val _31Days = mutableListOf(1, 3, 5, 7, 8, 10, 12)
            val list = mutableListOf<String>()
            val days = if (_31Days.contains(month)) {
                31
            } else if (month == 2) {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) { //闰年
                    29
                } else {
                    28
                }
            } else {
                30
            }
            for (i in 1..days) {
                list.add(i.toString())
            }
            return list
        }

        /**
         * 给小于10的数加个0，保持两位数的格式
         *
         * @param value
         * @return
         */
        fun add0(value: Int): String {
            return if (value < 10) {
                "0$value"
            } else value.toString()
        }

        fun setMinute(): List<String> {
            val minuteList = mutableListOf<String>()
            for (i in 0..59) {
                minuteList.add(i.toString())
            }
            return minuteList
        }
    }

    object CurData {
        /**
         * 当前日期  年月日
         *
         * @return
         */
        fun curDate(): String {
            val calendar: Calendar = Calendar.getInstance()
            return curStringYear(calendar) + "-" + add0(curStringMonth(calendar)) + "-" +
                    add0(curStringDay(calendar))
        }

        fun curStringYear(calendar: Calendar?): String {
            val calendar = calendar ?: Calendar.getInstance()
            return calendar.get(Calendar.YEAR).toString()
        }

        fun curStringMonth(calendar: Calendar?): String {
            val calendar = calendar ?: Calendar.getInstance()
            return (calendar.get(Calendar.MONTH) + 1).toString()
        }

        fun curStringDay(calendar: Calendar?): String {
            val calendar = calendar ?: Calendar.getInstance()
            return calendar.get(Calendar.DAY_OF_MONTH).toString()
        }

        fun curIntYear(calendar: Calendar?): Int {
            val calendar = calendar ?: Calendar.getInstance()
            return calendar.get(Calendar.YEAR)
        }

        /**
         * 当前时间时分秒
         *
         * @return
         */
        fun curTime(): String {
            val calendar: Calendar = Calendar.getInstance()
            return add0(curStringHour(calendar)) + ":" + add0(curStringMinute(calendar)) + ":" + add0(
                curStringSecond(calendar)
            )
        }

        fun curStringHour(calendar: Calendar?): String {
            val calendar = calendar ?: Calendar.getInstance()
            return calendar.get(Calendar.HOUR_OF_DAY).toString()
        }

        fun curStringMinute(calendar: Calendar?): String {
            val calendar = calendar ?: Calendar.getInstance()
            return calendar.get(Calendar.MINUTE).toString()
        }

        fun curStringSecond(calendar: Calendar?): String {
            return (calendar ?: Calendar.getInstance()).get(Calendar.SECOND).toString()
        }
    }

    /**
     * 给小于10的数加个0，保持两位数的格式
     *
     * @param value
     * @return
     */
    fun add0(value: Int): String {
        return if (value < 10) {
            "0$value"
        } else value.toString()
    }

    fun add0(value: String): String {
        val data = value.toIntOrNull() ?: 0
        return add0(data)
    }
}