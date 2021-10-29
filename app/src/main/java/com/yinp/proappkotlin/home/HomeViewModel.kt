package com.yinp.proappkotlin.home

import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.WanAndroid.NET_TYPE_A
import com.yinp.proappkotlin.WanAndroid.NET_TYPE_B
import com.yinp.proappkotlin.WanAndroid.NET_TYPE_C
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.yinp.proappkotlin.study.wanAndroid.data.WanHomeListData
import com.yinp.proappkotlin.web.data.BaseRespData
import com.yinp.proappkotlin.web.data.WanAndroidData
import com.yinp.proappkotlin.web.disposeNetOuter
import com.zhmyzl.mykotlin.network.RetrofitUtil
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.home
 * describe  :
 */
class HomeViewModel : BaseViewModel() {
    private val _wanResultData = MutableStateFlow(BaseRespData<List<HomeBannerData>>())
    val wanResultData: StateFlow<BaseRespData<List<HomeBannerData>>> = _wanResultData
    val channel = Channel<WanAndroidData<*>>()

    private val _wanHomeListData =
        MutableStateFlow(BaseRespData<WanHomeListData>())
    val wanHomeListData: StateFlow<BaseRespData<WanHomeListData>> = _wanHomeListData

    private val _wanHomeListData2 = MutableStateFlow(BaseRespData<List<WanHomeListData.Data>>())
    val wanHomeListData2: StateFlow<BaseRespData<List<WanHomeListData.Data>>> = _wanHomeListData2

    /**
     * 获取banner的显示列表数据
     */
    fun getBannerList() {
        viewModelScope.launch {
            disposeNetOuter(_wanResultData, channel, NET_TYPE_A) {
                RetrofitUtil.wandroidApiService.getBannerList()
            }
        }
    }

    /**
     * 获取首页数据
     */
    fun getListInfo(size: Int) {
        viewModelScope.launch {
            disposeNetOuter(_wanHomeListData, channel, NET_TYPE_B) {
                RetrofitUtil.wandroidApiService.getHomArticleList(size)
            }
        }
    }

    /**
     * 获取首页置顶文章
     */
    fun getStickList() {
        viewModelScope.launch {
            disposeNetOuter(_wanHomeListData2, channel, NET_TYPE_C) {
                RetrofitUtil.wandroidApiService.getStickList()
            }
        }
    }
}