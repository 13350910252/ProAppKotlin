package com.yinp.proappkotlin.base.bean

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.base.bean
 * describe  :
 */
data class BaseBean(
    var title: String? = "",
    var id: String? = "",
    var url: String? = "",
    var url2: String? = "",
    var content: String? = ""
) {
    constructor(title: String?, url: String?, url2: String?) : this() {
        this.title = title
        this.url = url
        this.url2 = url2
    }

    constructor(title: String?, url: String?) : this() {
        this.title = title
        this.url = url
    }

    constructor(title: String?, url: String?, url2: String?, content: String?) : this() {
        this.title = title
        this.url = url
        this.url2 = url2
        this.content = content
    }
}